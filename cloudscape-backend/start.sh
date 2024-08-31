#!/bin/bash

# jenv 설치 (이미 설치되어 있다면 넘어감)
if ! type jenv > /dev/null 2>&1; then
  brew install jenv
  echo "jenv installed."
else
  echo "jenv is already installed."
fi


if ! ls /Library/Java/JavaVirtualMachines | grep -q 'corretto-21'; then
  echo "Installing Amazon Corretto 21..."

  # Amazon Corretto 21 다운로드
  curl -L https://corretto.aws/downloads/latest/amazon-corretto-21-aarch64-macos-jdk.tar.gz --output amazon-corretto-21-aarch64-macos-jdk.tar.gz

  # 압축 해제
  mkdir -p /Library/Java/JavaVirtualMachines
  tar -xzf amazon-corretto-21-aarch64-macos-jdk.tar.gz -C /Library/Java/JavaVirtualMachines

  echo "Amazon Corretto 21 installed."
else
  echo "Amazon Corretto 21 is already installed."
fi

# jenv에 Corretto 21이 이미 추가되어 있는지 확인
if ! jenv versions | grep -q 'corretto.*-21'; then
  # jenv에 새로운 Java 버전 추가
  jenv add /Library/Java/JavaVirtualMachines/amazon-corretto-21.jdk/Contents/Home
  jenv global corretto64-21.0.2
  echo "Amazon Corretto 21 added to jenv."
else
  echo "Amazon Corretto 21 is already added to jenv."
  jenv global corretto64-21.0.2
fi

# 현재 설치된 Java 버전 확인
echo $(ls /Library/Java/JavaVirtualMachines)

# 변경 사항 적용
source ~/.zshrc

# Spring Boot 애플리케이션 구동
./gradlew clean build
./gradlew bootRun --args='--spring.profiles.active=local' &