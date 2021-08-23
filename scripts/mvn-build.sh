cp spring-resources/logback-spring.xml "../services/basket/src/main/resources" && \
cp spring-resources/logback-spring.xml "../services/catalog/src/main/resources" && \
cp spring-resources/logback-spring.xml "../services/discount/src/main/resources" && \
cp spring-resources/logback-spring.xml "../../cloud-core/eureka/src/main/resources" && \
cp spring-resources/logback-spring.xml "../../cloud-core/config/src/main/resources" && \
cp spring-resources/logback-spring.xml "../../cloud-core/gateway/src/main/resources" && \
cp spring-resources/git-config.properties "../../cloud-core/config/src/main/resources" && \
cd .. \
&& cd lib \
  && cd security-lib && mvn clean install -DskipTests \
&& cd .. \
  && cd utils-lib && mvn clean install -DskipTests \
&& cd .. && cd .. && cd .. \
&& cd cloud-core \
  && cd config && mvn clean package -DskipTests \
&& cd .. \
  && cd eureka && mvn clean package -DskipTests \
&& cd .. \
  && cd gateway && mvn clean package -DskipTests \
&& cd .. && cd .. && cd e-shop-api \
&& cd services \
  && cd basket && mvn clean package -DskipTests \
&& cd .. \
  && cd catalog && mvn clean package -DskipTests \
&& cd .. \
  && cd discount && mvn clean package -DskipTests \
&& cd .. && cd .. \
&& exit
