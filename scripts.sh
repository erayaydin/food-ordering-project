docker-compose -f common.yml -f zookeeper.yml up
docker-compose -f common.yml -f kafka_cluster.yml up
docker-compose -f db.yml up

docker-compose -f db.yml up
docker-compose -f common.yml -f zookeeper.yml up
docker-compose -f common.yml -f kafka_cluster.yml up
docker-compose -f common.yml -f kafka_cluster.yml up schema-registry
docker-compose -f common.yml -f kafka_cluster.yml up redpanda