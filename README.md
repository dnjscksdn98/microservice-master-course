<a><img src="https://i.ibb.co/GQmXsrF/msa.jpg" alt="msa" border="0" width="700"></a>

## Microservices Master Course with Spring

[![](https://img.shields.io/badge/docs-velog-green)](https://velog.io/@dnjscksdn98/series/Spring-Boot-MSA)
[![](https://img.shields.io/badge/references-udemy-green)](https://www.udemy.com/course/spring-boot-microservices-and-spring-cloud/)

## 🚀 Eureka 개요
**정의**
- ```Client-side Service Discovery```
- Eureka는 주소가 동적으로 변하는 AWS와 같은 Cloud 시스템 환경에서 클라이언트가 서비스 인스턴스를 호출할 수 있도록 각 서비스들의 ```IP / Port / Instance ID``` 를 가지고 있는 REST 기반의 미들웨어 서버입니다.
- 즉, Service Registry를 제공하고 관리해주는 서비스입니다.

### ✔ Eureka 용어 정리
**Eureka 행동 관련**
- ```Service Registration```
  - 서비스가 자기 자신의 정보를 Eureka 서버에 등록하는 행동

- ```Service Registry```
  - 등록된 서비스들의 정보 목록
  
- ```Service Discovery```
  - 서비스 클라이언트가 요청을 보내고자 하는 대상의 정보를 Service Registry를 통해 발견하는 과정
  
**Eureka 구성 요소 관련**
- ```Eureka Client```
  - 서비스들의 위치 정보를 알아내기 위해 Eureka에 정보를 요청하는 서비스

- ```Eureka Service```
  - Eureka Client에 의해 발견의 대상이 되도록 Eureka에 등록을 요청한 서비스
  
- ```Eureka Server```
  - Eureka Service가 자기 자신을 등록(Service Registration)하는 서버이자, Eureka Client가 등록된 서비스 목록(Service Registry)를 요청하는 서버

### ✔ 클라이언트 사이드 디스커버리

서비스 인스턴스의 네트워크 위치를 찾고 로드밸런싱하는 역할을 클라이언트가 담당합니다.

<a><img src="https://www.nginx.com/wp-content/uploads/2016/04/Richardson-microservices-part4-2_client-side-pattern.png" width="500"></a>

서비스 인스턴스는 시작될 때 자신의 네트워크 주소를 **서비스 레지스트리(Service Registry)** 에 등록하고, 서비스 레지스트리는 각 서비스 인스턴스의 상태를 계속해서 체크합니다.

클라이언트는 서비스 레지스트리에 등록된 인스턴스 중 하나를 골라서 요청을 보내는 방식으로 **로드 밸런싱(Load Balancing)** 이 이루어집니다. 그리고 인스턴스가 종료되면 서비스 레지스트리에 등록된 정보는 삭제됩니다.

**Netflix OSS**가 클라이언트 사이드 디스커버리 패턴의 좋은 예입니다. **Netflix Eureka**는 서비스 레지스트리로 서비스 인스턴스의 등록과 가용한 인스턴스를 찾는 REST API를 제공합니다. 그리고 **Netflix Ribbon**은 Eureka와 같이 동작하는 IPC 클라이언트로 가능한 서비스 인스턴스 간 로드 밸런싱을 해줍니다.

### ✔ 서버 사이드 디스커버리

<a><img src="https://www.nginx.com/wp-content/uploads/2016/04/Richardson-microservices-part4-3_server-side-pattern.png" width="600"></a>

클라이언트 사이드 디스커버리는 Service Client가 Service Registry에서 직접 서비스의 위치를 찾아서 호출하는 방식인 반면에 서버 사이드 디스커버리는 일종의 Proxy 서버인 로드 밸런서로 요청을 먼저 보냅니다. 그리고 로드 밸런서는 Service Registry를 조회해서 가용한 인스턴스를 찾고 그 중 선택해서 요청을 라우팅하는 방식입니다. Service Registry에 등록되는 방식은 클라이언트 사이드 디스커버리와 동일합니다.

**AWS Elastic Load Balancer(ELB)** 가 서버 사이드 디스커버리 패턴의 좋은 예입니다. ELB는 일반적으로 인터넷에서 들어오는 외부 트래픽을 로드 밸런싱하는 데 사용되고, **VPC(Virtual Private Cloud)** 에서 내부 트래픽을 처리할 때 사용되기도 합니다. 클라이언트에서 DNS 이름을 이용해 ELB로 요청을 보내면 ELB는 등록된 EC2(Elastic Compute Cloud) 인스턴스나 ECS(EC2 Container Service) 컨테이너 사이에서 부하를 분산합니다. 

### ✔ 서비스 레지스트리

서비스 레지스트리는 각 서비스 인스턴스의 네트워크 위치 정보를 저장하는 데이터베이스로 항상 최신 정보를 유지해야 하며 고가용성이 필요합니다.

대표적인 서비스 레지스트리인 **Netflix Eureka**는 서비스 인스턴스를 등록하고 조회하는 REST 기반 API를 제공합니다. 각 서비스 인스턴스는 ```POST``` 요청으로 자신의 네트워크 위치를 등록하고 30초마다 ```PUT``` 요청으로 자신의 정보를 갱신합니다. 등록된 서비스 정보는 ```DELETE``` 요청이나 타임 아웃으로 삭제됩니다. 그리고 등록된 서비스 정보는 ```GET``` 요청으로 조회할 수 있습니다.

### ✔ 서비스 등록

각 서비스는 서비스 레지스트리에 각자의 정보를 등록하고 해제해야 하는데 여기에는 두 가지 방식이 존재합니다.

- 셀프 등록 패턴 (Self Registration Pattern): 서비스 스스로 등록을 관리
- 써드 파티 등록 패턴 (3rd Party Registration Pattern): 제 3의 시스템에서 등록을 관리

**1) 셀프 등록 패턴**

등록과 관리를 하는 주체가 서비스인 방식입니다. 각 서비스는 서비스 레지스트리에 자신의 정보를 등록하고, 필요하다면 주기적으로 자신이 살아 있다는 신호(Heartbeat)를 계속 전송합니다. 만약 이 정보가 일정 시간이 지나도 오지 않는다면 서비스에 문제가 발생한 것으로 보고 등록이 해제됩니다. 그리고 서비스가 종료될 때는 등록을 해제합니다. 

<a><img src="https://www.nginx.com/wp-content/uploads/2016/04/Richardson-microservices-part4-4_self-registration-pattern.png" width="600"></a>

앞서 살펴본 **Eureka Client**가 이에 해당합니다. Spring에서는 ```@EnableEurekaClient``` 어노테이션을 이용해 쉽게 구현할 수 있습니다.

**2) 써드 파티 등록 패턴**

외부에서 서비스 등록을 관리하는 방법도 있습니다. 서비스 등록을 관리하는 **서비스 레지스트라(Service Registrar)** 를 따로 두는 것입니다. 서비스 레지스트라는 각 서비스 인스턴스의 변화를 폴링(Polling)이나 이벤트 구독으로 감지해서 서비스 레지스트리에 계속 업데이트합니다.

<a><img src="https://www.nginx.com/wp-content/uploads/2016/04/Richardson-microservices-part4-5_third-party-pattern.png" width="600"></a>

## 🚀 Eureka Server 환경 구축

**1) 의존성 설정**
```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

**2) @EnableEurekaServer 어노테이션 추가**

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
```

**3) 프로퍼티 설정(Config Server 내의 eureka-server.yml)**

- ```registerWithEureka: false``` -> Eureka Service에 자신을 등록하지 않는다
- ```fetchRegistry: false``` -> Registry 정보를 로컬에 캐싱하지 않는다

```yml
eureka:
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://localhost:8010/eureka/
```

## 🌟 Eureka Client 환경 구축

**1) 의존성 설정**

```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

**2) @EnableEurekaClient 어노테이션 추가**

```java
@SpringBootApplication
@EnableEurekaClient
public class LicenseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicenseServiceApplication.class, args);
	}
}
```

**3) 프로퍼티 설정(Config Server 내의 license-service.yml)**

- ```preferIpAddress: true``` -> 서비스의 호스트 이름이 아닌 IP 주소를 Eureka에 등록하도록 지정

> **✔ IP 주소를 선호하는 이유**
>
기본적으로 Eureka는 호스트 이름으로 접속하는 서비스를 등록합니다. 이것은 DNS가 지원된 호스트 이름을 할당하는 서버 기반 환경에서 잘 동작하기 때문입니다. 그러나 컨테이너 기반의 환경에서는 DNS 엔트리가 없는 임의로 생성된 호스트 이름을 부여받아 시작합니다. 따라서 해당 값을 true로 설정하지 않는다면 해당 컨테이너에 대한 DNS 엔트리가 없으므로 클라이언트 애플리케이션은 호스트 이름의 위치를 정상적으로 얻지 못하게 됩니다.

```yml
eureka:
  instance:
    preferIpAddress: true
  client:
    registerWithEureka: true
    fetchRegistry: true
    serviceUrl:
      defaultZone: http://localhost:8010/eureka/
```

## 🚀 Spring Cloud Config Server란?

Spring Cloud Config Server는 스프링 부트로 만든 REST 기반의 애플리케이션입니다. 그리고 분산 시스템에서 환경설정을 외부로 분리하여 관리할 수 있는 기능을 제공해줍니다. Config Server를 사용하여 모든 환경(개발, 테스트, 프로덕션 등)에 대한 어플리케이션들의 속성을 한 곳에서 관리할 수 있습니다.

<a><img src="https://www.canchito-dev.com/public/blog/wp-content/uploads/2019/07/Spring-Boot-Config-Server-and-client-side-support-for-externalized-configuration.png" width="600"></a>

### 장점
- 설정 관리의 용이성
- 운영중에 서버 빌드 및 배포를 다시 할 필요 없이 환경설정 변경 가능

### 기능
**Spring Cloud Config Server**
- 환경설정(name-value pair, YAML 파일)을 위한 HTTP, 리소스 기반 API
- 속성 값 암호화 및 암호 해독 (대칭 또는 비대칭)
- @EnableConfigServer 어노테이션을 사용하여 쉽게 Spring Boot 어플케이션에 적용

**Config Client(for Spring Boot 어플리케이션)**
- Config Server에 붙어 원격 속성 소스로 Spring 환경 초기화
- 속성 값 암호화 및 암호 해독 (대칭 또는 비대칭)

## Config Server 구조
**의존성 설정**
```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-config-server</artifactId>
</dependency>
```
**애플리케이션 프로퍼티 설정(application.yml)**
```yml
spring:
  application:
    name: {application.name}

  profiles:
    active: git

  cloud:
    config:
      server:
        git:
          uri: {remote.property.source.uri}
          username: {username}
          password: {password}
```

**@EnableConfigServer 어노테이션 추가**
```java
@SpringBootApplication
@EnableConfigServer
public class PhotoAppApiConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiConfigServerApplication.class, args);
	}
}
```

### Spring Cloud Config Client 구조
**의존성 설정**
```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

**Remote Git Repository에 프로퍼티 파일 생성**
  - 우선순위: {application.name}-{profile}.yml > {application.name}.yml > application.yml

**부트스트래핑 설정(bootstrap.yml)**
```yml
spring:
  application:
    name: {application.name}

  profiles:
    active: {profile}

  cloud:
    config:
      uri: {config.server.uri}
```
- 이때, ```spring.application.name```은 원격 저장소에 있는 파일명이랑 동일해야 합니다.
- 원격 저장소에 디렉터리별로 관리중이라면 ```spring.cloud.config.server.git.searchPaths``` 를 추가해서 경로를 지정해주면 됩니다.

## 🚀 마이크로서비스 간의 통신
- 모놀리식 애플리케이션에서는 단순하게 다른 메소드나 함수를 호출하면 됩니다.
- 하지만 마이크로서비스에서는 서비스 단위로 나뉘어져 있는 분산 시스템이기 때문에 서비스 간의 통신이 필요합니다.
- 이러한 통신을 **프로세스 간 통신(Inter-Process Communication)**이라고 합니다.

<a><img src="https://cdn-1.wp.nginx.com/wp-content/uploads/2015/07/Richardson-microservices-part3-monolith-vs-microservices-1024x518.png" width="600"></a>

### 통신 방식
- 통신 방식은 먼저 호출하는 쪽과 호출당하는 쪽의 수로 구분해볼 수 있습니다.
- 하나의 요청이 하나의 서비스를 실행하면 일대일(One-to-One)
- 하나의 요청이 여러 서비스를 실행하면 일대다(One-to-Many)

- 그리고 동기(Synchronous)와 비동기(Asynchronous)로도 구분할 수 있습니다.
- 동기 방식은 요청을 보내고 응답이 올 때까지 기다리는 방식으로 이후 동작은 멈춘 상태가 됩니다. 그리고 응답을 받은 후에 처리합니다.
- 반대로 비동기 방식은 요청을 보내고 응답이 올 때까지 기다리지 않고 다음을 실행합니다. 

<a><img src="https://i.ibb.co/N3g35LG/image.png" alt="image" border="0" width="600"></a>

- **1) 요청 / 응답**: 요청을 보내고 응답이 올 때까지 기다립니다.
- **2) 알림**: 요청을 보내기만 합니다. 모바일의 푸시 알림을 생각하시면 됩니다.
- **3) 요청 / 비동기 응답**: 요청을 보내면 비동기로 응답이 옵니다.
- **4) 퍼블리시 / 구독**: 등록된 서비스들에 요청을 보냅니다. 요청을 받은 서비스들은 각자 로직을 처리합니다.
- **5) 퍼블리시 / 비동기 응답**: 위와 같지만 비동기 형태로 응답을 보냅니다.

- 애플리케이션에 따라서 하나의 방식만으로도 충분하기도 하고, 여러 방식을 함께 사용하기도 합니다. 다음 택시 호출 서비스를 예로 들어보겠습니다.

<a><img src="https://cdn-1.wp.nginx.com/wp-content/uploads/2015/07/Richardson-microservices-part3-taxi-service-1024x609.png" width="600"></a>

- 1) 승객이 모바일로 픽업을 요청합니다. 이 요청은 여행 관리 서비스를 호출합니다 (**알림**)
- 2) 여행 관리 서비스는 승객 관리 서비스에서 승객 정보를 확인합니다 (**요청/응답**)
- 3) 여행 관리 서비스는 해당 여행을 디스패처와 가까운 택시기사에게 보냅니다 (**퍼블리시/구독**)

### 동기 방식 - REST

<a><img src="https://cdn.wp.nginx.com/wp-content/uploads/2015/07/Richardson-microservices-part3-rest-1024x397.png" width="600"></a>

- 택시 호출 애플리케이션을 예로 들어보겠습니다.
- 승객이 택시를 부를 때 REST API를 호출하게 됩니다. 이때, POST 방식으로 ```/trips``` 를 호출하게 됩니다.
- 여행 관리 서비스에서 승객에 대한 정보를 조회하기 위해, 승객 관리 서비스가 제공하는 REST API를 사용해서 GET 방식으로 ```/passengers/{passengerId}``` 로 요청을 보냅니다.
- 그럼 승객 ID로 승객 정보를 조회해서 해당 정보를 가지고 여행 정보를 최종적으로 생성하게 됩니다.

- REST는 동기 방식이므로 요청한 마이크로서비스는 응답을 받은 마이크로서비스가 요청을 처리할 때까지 기다리게 됩니다.
- 그리고 항상 일대일 통신 방식을 사용하게 됩니다.

### 비동기 방식 - 메시징
- 비동기 방식은 메시지를 보내놓고 응답을 기다리지 않습니다.
- 이 메시지는 Header와 Body로 구성되어 있고 Channel을 통해 전송됩니다.
- 메시지는 한 곳에서만 보낼 수 있고(일대일), 퍼블리시/구독 모델을 따라 여러 곳에 메시지를 보낼 수 있습니다(일대다).
- 즉, 그 메시지를 받겠다고 구독해놓은 서비스에게 모두 메시지가 전송되는 것입니다.

<a><img src="https://cdn.wp.nginx.com/wp-content/uploads/2015/07/Richardson-microservices-part3-pub-sub-channels-1024x639.png" width="600"></a>

- 이러한 메시징 방식은 클라이언트와 서비스 사이의 의존도를 줄여줍니다.
- 동기 방식은 클라이언트와 서비스가 서로를 알아야 하고 직접 통신하는 구조이지만,
- 메시징 방식은 그 사이에 메시징 시스템이 들어가서 간접적으로 통신하기 때문입니다.
- 일대일 통신 뿐만 아니라 일대다 통신을 지원하는 것도 장점입니다.
- 메시지를 전송하는 표준 프로토콜은 AMQP입니다.
- 그리고 오픈소스 메시징 시스템은 대표적으로 RabbitMQ와 Apache Kafka가 있습니다.

## 🚀 서비스 디스커버리를 사용해 서비스 검색
- 여행 관리 서비스는 어떻게 승객 관리 서비스를 알고 요청을 보낼까요?
- 이때 사용할 수 있는 기술은 **Eureka Discovery Service**입니다.
- 해당 인스턴스가 실행이 될때 Eureka Discovery Service로 등록을 하게 됩니다.
- 그래서 Eureka는 현재 실행되고 있는 모든 인스턴스들의 주소와 포트 번호를 알게 됩니다.
- 그러므로 여행 관리 서비스는 Eureka를 통해서 요청을 보내야할 올바른 주소와 포트번호를 알게 되서 요청을 보낼 수 있게 됩니다.

### ✔ RestTemplate을 통한 통신
**RestTemplate 빈 등록**

- ```@LoadBalanced``` : 스프링 클라우드가 Ribbon을 지원하는 RestTemplate 클래스를 생성하도록 지정

```java
@SpringBootApplication
@EnableEurekaClient
public class PhotoAppApiUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsersApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
```

**RestTemplate Client 생성**

- ```restTemplate.exchange({url}, {HTTP Method}, {HTTP Entity(Header/Body)}, {response type}, {parameter})```

- RestTemplate 호출에서 서비스의 물리적 위치를 사용하는 대신 호출하려는 서비스의 유레카 서비스 ID를 사용합니다(spring.application.name).

- RestTemplate이 Eureka한테 ```organization-service```에 대한 모든 주소들을 먼저 물어보고, 받은 주소들을 가지고 로드 밸런싱(라운드 로빈 방식)을 통해서 요청을 보내게 됩니다.

```java
@Component
public class OrganizationRestTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://organization-service/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null,
                        Organization.class,
                        organizationId
                );

        return restExchange.getBody();
    }
}
```

### ✔ Feign Client를 통한 통신
- REST 기반 서비스 호출을 추상화한 Spring Cloud Netflix 라이브러리
- 선언적 방식(Declarative REST Client)
- 인터페이스를 통해 클라이언트 측 프로그램 작성
- Spring이 런타임에 구현체를 제공

**의존성 설정**
```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

**@EnableFeignClients 어노테이션 추가**

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class PhotoAppApiUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsersApplication.class, args);
	}
}
```

**Feign Client 인터페이스 생성**

- ```@FeignClient(name = "{service.name}")``` -> 인터페이스를 대표할 서비스 애플리케이션 지정

```java
@FeignClient(name = "organization-service", path = "v1/organizations/")
public interface OrganizationFeignClient {

    @GetMapping(path = "{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Organization getOrganization(@PathVariable("organizationId") String organizationId);

}
```
  
**Feign Client에 대한 로깅 설정**

- 패키지 전체 활성화
```yml
logging:
  level:
    com:
      alexcode:
        photoapp:
          api:
            users:
              PhotoAppApiUsers:
                feign: DEBUG
```    
- 특정 클라이언트만 활성화
```yml
logging:
  level:
    com:
      alexcode:
        photoapp:
          api:
            users:
              PhotoAppApiUsers:
                feign:
                  AlbumServiceClient: DEBUG
```    

- 로깅 레벨에 대한 빈 생성

```java
@Configuration
public class FeignClientConfig {

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
```
- **NONE**: 로깅 없음, 디폴트값
- **BASIC**: 요청 함수, URL, 응답 상태코드만을 로깅
- **HEADERS**: BASIC에다가 요청과 응답의 Header까지 로깅
- **FULL**: 요청과 응답의 Body, Header 그리고 그 외의 메타데이터까지 로깅


### References
- https://futurecreator.github.io/2018/10/18/service-discovery-in-microservices/
- https://futurecreator.github.io/2018/10/04/inter-process-communication-in-microservices/
- https://futurecreator.github.io/2018/09/14/microservices-with-api-gateway/
- https://coe.gitbook.io/guide/service-discovery/eureka
