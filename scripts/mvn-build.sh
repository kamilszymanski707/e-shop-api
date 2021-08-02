cd .. \
&& cd lib \
  && cd security-lib && mvn clean install -DskipTests \
&& cd .. \
  && cd utils-lib && mvn clean install -DskipTests \
&& cd .. && cd .. \
&& cd core \
  && cd config && mvn clean package -DskipTests \
&& cd .. \
  && cd eureka && mvn clean package -DskipTests \
&& cd .. \
  && cd gateway && mvn clean package -DskipTests \
&& cd .. && cd .. \
&& cd services \
  && cd basket && mvn clean package -DskipTests \
&& cd .. \
  && cd catalog && mvn clean package -DskipTests \
&& cd .. \
  && cd discount && mvn clean package -DskipTests \
&& cd .. && cd .. \
&& exit
