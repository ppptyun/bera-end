FROM ubuntu:16.04  
RUN apt-get -y update && apt-get -y upgrade
#RUN apt-get -y install openjdk-8-jdk wget
RUN mkdir /usr/local/tomcat
RUN wget https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.90/bin/apache-tomcat-8.5.90.tar.gz -O /tmp/tomcat.tar.gz
RUN cd /tmp && tar xvfz tomcat.tar.gz
RUN cp -Rv /tmp/apache-tomcat-8.5.90/* /usr/local/tomcat/
RUN cd /usr/local/tomcat/bin/ && ls -l
RUN cd /usr/local/tomcat/webapps/ && ls -l
COPY target/DealClientCredential-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/DealClientCredential-1.0-SNAPSHOT.war
RUN cd /usr/local/tomcat/webapps/ && ls -l
EXPOSE 8080
#CMD /usr/local/tomcat/bin/catalina.sh run
#CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
CMD ["/usr/local/tomcat/bin/startup.sh", "run"]
RUN cd /usr/local/tomcat/webapps/ && ls -l