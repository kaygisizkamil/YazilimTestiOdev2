FROM adoptopenjdk:11-jdk-hotspot

WORKDIR /app
COPY src/program /app/src/program
COPY tests/systemTests /app/tests/systemTests
COPY libs /app/libs

# Set the PATH environment variable to include the Java and Chrome binaries
ENV PATH="/usr/local/openjdk/bin:/usr/local/bin:${PATH}"

RUN apt-get update && apt-get install -y wget unzip gnupg2 \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update && apt-get install -y google-chrome-stable \
    && wget -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/`curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE_113`/chromedriver_linux64.zip \
    && unzip /tmp/chromedriver.zip -d /usr/local/bin/ \
    && chmod +x /usr/local/bin/chromedriver

# Copy and compile the Java source files
COPY libs/selenium-server-4.9.1.jar /app/libs/selenium-server-4-9-1.jar
COPY libs/junit-platform-console-standalone-1.9.2.jar /app/libs/junit-platform-console-standalone.jar

RUN javac -d /app -cp ".:/app/libs/*" /app/src/program/*.java /app/tests/systemTests/*.java

# Expose port 99 for the application
EXPOSE 99

CMD java -jar /app/libs/selenium-server-4.9.1.jar & \
    sleep 5 && \
    java -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver -cp ".:/app/libs/*:/app" program.TestMenu
