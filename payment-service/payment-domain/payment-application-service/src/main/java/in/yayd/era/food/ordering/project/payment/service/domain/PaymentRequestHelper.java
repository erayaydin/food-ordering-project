package in.yayd.era.food.ordering.project.payment.service.domain;

import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;
import in.yayd.era.food.ordering.project.payment.service.domain.dto.PaymentRequest;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.CreditEntry;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.CreditHistory;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.Payment;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentEvent;
import in.yayd.era.food.ordering.project.payment.service.domain.exception.PaymentApplicationServiceException;
import in.yayd.era.food.ordering.project.payment.service.domain.mapper.PaymentDataMapper;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.repository.CreditEntryRepository;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class PaymentRequestHelper {

    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;
    private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
    private final PaymentCancelledMessagePublisher paymentCancelledEventDomainEventPublisher;
    private final PaymentFailedMessagePublisher paymentFailedEventDomainEventPublisher;

    public PaymentRequestHelper(
            PaymentDomainService paymentDomainService,
            PaymentDataMapper paymentDataMapper,
            PaymentRepository paymentRepository,
            CreditEntryRepository creditEntryRepository,
            CreditHistoryRepository creditHistoryRepository,
            PaymentCompletedMessagePublisher paymentCompletedMessagePublisher, PaymentCancelledMessagePublisher paymentCancelledEventDomainEventPublisher, PaymentFailedMessagePublisher paymentFailedEventDomainEventPublisher) {
        this.paymentDomainService = paymentDomainService;
        this.paymentDataMapper = paymentDataMapper;
        this.paymentRepository = paymentRepository;
        this.creditEntryRepository = creditEntryRepository;
        this.creditHistoryRepository = creditHistoryRepository;
        this.paymentCompletedMessagePublisher = paymentCompletedMessagePublisher;
        this.paymentCancelledEventDomainEventPublisher = paymentCancelledEventDomainEventPublisher;
        this.paymentFailedEventDomainEventPublisher = paymentFailedEventDomainEventPublisher;
    }

    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest) {
        log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest);
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent =
                paymentDomainService.validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages, paymentCompletedMessagePublisher, paymentFailedEventDomainEventPublisher);
        persistDbObjects(payment, creditEntry, creditHistories, failureMessages);

        return paymentEvent;
    }

    @Transactional
    public PaymentEvent persistCancelPayment(PaymentRequest paymentRequest) {
        log.info("Received payment rollback event for order id: {}", paymentRequest.getOrderId());
        Optional<Payment> maybePayment = paymentRepository.findByOrderId(UUID.fromString(paymentRequest.getOrderId()));
        if (maybePayment.isEmpty()) {
            log.error("Payment with order id: {} could not be found!", paymentRequest.getOrderId());
            throw new PaymentApplicationServiceException("Payment with order id: " + paymentRequest.getOrderId() + " could not be found!");
        }
        Payment payment = maybePayment.get();
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService
                .validateAndCancelPayment(payment, creditEntry, creditHistories, failureMessages, paymentCancelledEventDomainEventPublisher, paymentFailedEventDomainEventPublisher);
        persistDbObjects(payment, creditEntry, creditHistories, failureMessages);

        return paymentEvent;
    }

    private CreditEntry getCreditEntry(CustomerId customerId) {
        Optional<CreditEntry> maybeCreditEntry = creditEntryRepository.findByCustomerId(customerId);

        if (maybeCreditEntry.isEmpty()) {
            log.error("Could not find credit entry for customer: {}", customerId.getValue());
            throw new PaymentApplicationServiceException("Could not find credit entry for customer: " + customerId.getValue());
        }

        return maybeCreditEntry.get();
    }

    private List<CreditHistory> getCreditHistory(CustomerId customerId) {
        Optional<List<CreditHistory>> maybeCreditHistories = creditHistoryRepository.findByCustomerId(customerId);

        if (maybeCreditHistories.isEmpty()) {
            log.error("Could not find credit history for customer: {}", customerId.getValue());
            throw new PaymentApplicationServiceException("Could not find credit history for customer: " + customerId.getValue());
        }

        return maybeCreditHistories.get();
    }

    private void persistDbObjects(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        paymentRepository.save(payment);

        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry);
            creditHistoryRepository.save(creditHistories.get(creditHistories.size() - 1));
        }
    }
}
