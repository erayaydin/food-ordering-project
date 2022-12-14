#!/usr/bin/env bash
sudo rm -Rf volumes
mkdir -p volumes/kafka/broker-1
mkdir volumes/kafka/broker-2
mkdir volumes/kafka/broker-3
mkdir volumes/order-db
mkdir volumes/pgadmin
sudo chown -R 5050:5050 volumes/pgadmin
mkdir -p volumes/zookeeper/data
mkdir volumes/zookeeper/transactions