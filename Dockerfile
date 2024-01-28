FROM ubuntu:16.04
ENV DEBIAN_FRONTEND noninteractive
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

RUN apt-get update -y \
	&& apt-get install -y software-properties-common \
	&& add-apt-repository ppa:openjdk-r/ppa \
	&& apt-get install -y openjdk-8-jdk \
	&& rm -rf /var/lib/apt/lists/*

RUN apt-get update && apt-get install -y unzip \
	curl \
	zip \
	bzip2

RUN apt-get install -y libgconf2-4 \
	libnss3 \
	libxss1 \
	gconf-service

RUN apt-get install -y fonts-liberation \
 	libasound2 \
	libatk-bridge2.0-0 \
	libatk1.0-0 \
	libatspi2.0-0 \
	libcairo2 \
	libdrm2 \
	libgbm1 \
	libgtk-3-0 \
	libpango-1.0-0 \
	libxcomposite1 \
	libxdamage1 \
	libxfixes3 \
	libxkbcommon0 \
	libxrandr2 \
	wget \
	xdg-utils

ARG CHROME_VERSION=99.0.4844.82
ARG CHROMDRIVER_VERSION=99.0.4844.51
ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"
ARG BASE_URL=http://apachemirror.wuchna.com/maven/maven-3/${MAVEN_VERSION}/binaries

#step 2: install chrome
RUN curl http://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_$CHROME_VERSION-1_amd64.deb -o /chrome.deb
#RUN apt-get install -f /chrome.deb
RUN dpkg -i /chrome.deb
RUN rm /chrome.deb

#Step 3: Install chromedriver for Selenium
RUN mkdir -p /app/bin
RUN curl https://chromedriver.storage.googleapis.com/$CHROMDRIVER_VERSION/chromedriver_linux64.zip -o /tmp/chromedriver.zip \
    && unzip /tmp/chromedriver.zip -d /app/bin/ \
    && rm /tmp/chromedriver.zip
RUN chmod +x /app/bin/chromedriver

#ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"
#ARG BASE_URL=http://apachemirror.wuchna.com/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && echo "Downlaoding maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz \
  \
  && echo "Unziping maven" \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
  
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

COPY . .

WORKDIR /docker-app