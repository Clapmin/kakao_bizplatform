# 카카오 비즈플랫폼
## 목차
- [개발 환경](#개발-환경)
- [테이블 구조](#테이블-구조)
- [가정](#가정)
- [테스트 방법](#테스트-방법)

---

## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA Ultimate
    - OS: Windows10 
    - GIT
- Server
    - Java8
    - Spring Boot 2.3.11
    - JPA
    - H2
    - Gradle
    - Junit5

## 테이블 구조
### 결과 테이블(navi_result)
|Field|Type||
|---|---|---|
|Id|bigint|PK|
|transaction_id|varchar|거래 ID|
|work_approved|boolean|업무 인정여부(true인정/false불인정)|
|start_name|varchar|출발지|
|goal_name|varchar|목적지|
|arrival_time|datetime|도착시간|
|departure_time|datetime|출발시간|
|total_distance|int|출발지에서 목적지까지의 거리(meter)|
|moved_distance|int|이동거리(meter)|
|modify_date|datetime|수정시간|
|register_date|datetime|등록시간|


### 히스토리 테이블(navi_hist)
|Field|Type||
|---|---|---|
|Id|bigint|PK|
|trans_id|varchar|거래 ID|
|date_time|datetime|API 요청시간|
|latitude|double|위도|
|longitude|double|경도|
|moved_distance|int|시간상(date_time) 직전 Hist로 부터 이동한 거리|
|remain_distance|int|남은거리|
|request_type|varchar|리퀘스트 종류(StartNavi, UpdateLocation, Close)|
|modify_date|datetime|수정시간|
|register_date|datetime|등록시간|


## 가정
- 거리 계산방법
  - 테스트 데이터의 위도 경도값이 변경이 없어서, api 호출시 넘어온 remainDistance를 가지고 거리를 계산했습니다.
  - navi_hist 테이블에 데이터를 넣기전에, 이전 hist로 부터의 이동거리(멀어지거나, 가까워지는 경우 모두)를 계산했습니다.(목적지와의 거리가 항상 줄어들기만 하지는 않으므로)
  <br/>
  <br/>
  
- 제약조건 판단방법
```
> '내비 시작 후 24시간 동안 위치정보가 수신되지 않으면 결과 테이블에 저장되지 않습니다.'
```
  - 위 조건은 `Start`시점과 최초 `Update`된 시점의 시간차이를 계산해서 조건을 판단
  - Close시에 최후에 판단
  
  <br/>
  
```
> '1시간 동안 위치정보가 업데이트 되지 않는다면 결과 테이블에 저장하지 않음'
```
  - 위 조건은 직전 위치정보 update요청과, 현재 들어온 update요청의 시간차이를 계산해서 조건을 판단
  - 오직 UpdateLocation에 대해서 1시간 여부를 확인 



## 테스트 방법
- 접속 Base URI: `http://localhost:8080`

### 1. 네비 상태((start, update, close)변경 API
- Request
```
POST http://localhost:8080/navi
```
- Response
```json
{
  "code": "0",
  "message": "success",
  "body": null
}
```
- 모든 요청(start, update, close)은 API를 통해 처리됩니다.
  - NaviRequestDto의 형태는 요청 예시에 나온바와 같습니다.
```java
    @PostMapping("/navi")
    public CommonApiResponse changeNaviStatus(@RequestBody NaviRequestDto request) {
        naviService.changeNaviStatus(request);
        return CommonApiResponse.successResponse();
    }
```
```json
{
 "type": "StartNavi",
 "location": {
 "transId": "kakao20201113-0001",
 "dateTime": "202011131101020003",
 "totalDistance": 7000,
 "startName": "성남시청",
 "goalName": "카카오모빌리티",
 "lng": 127.567849497291,
 "lat": 36.957739187083654
 }
}
```
### 2. 결과조회 API
- Request
```
GET http://localhost:8080/navi/result
```
- Response
```json
{
  "code": "0",
  "message": "success",
  "body": [
    {
      "registerDate": "2021-06-09T02:03:22.519",
      "modifyDate": "2021-06-09T02:03:22.801",
      "id": 1,
      "transactionId": "kakao20201113-0001",
      "workApproved": true,
      "departureTime": "2020-11-13T11:01:02.003",
      "arrivalTime": null,
      "startName": "성남시청",
      "goalName": "카카오모빌리티",
      "totalDistance": 7000,
      "movedDistance": null,
      "points": [
        {
          "latitude": 36.957739187083654,
          "longitude": 127.567849497291
        }
      ]
    }
  ]
}
```

### 3. 네비 상태변경 히스토리 조회 API
- Request
```
GET http://localhost:8080/navi/hist
```
- Response
```json
{
  "code": "0",
  "message": "success",
  "body": [
    {
      "registerDate": "2021-06-09T02:03:22.794",
      "modifyDate": "2021-06-09T02:03:22.794",
      "id": 1,
      "transId": "kakao20201113-0001",
      "dateTime": "2020-11-13T11:01:02.003",
      "latitude": 36.957739187083654,
      "longitude": 127.567849497291,
      "movedDistance": 0,
      "requestType": "StartNavi",
      "remainDistance": 7000
    }
  ]
}
```

### 4. 별텀 데이터 자동등록 API
- 예시로 첨부된 테스트 데이터를 등록해주는 API입니다.
- Request
```
POST http://localhost:8080/navi/data
```
- Response
```json
{
  "code": "0",
  "message": "success",
  "body": null
}
```

### 5. 데이터 전체 삭제 API
- 테스트 진행을 위해 데이터 초기화를 하고싶은경우에는 아래 API를 호출할 수 있습니다.
- Request
```
POST http://localhost:8080/navi/data/delete
```
- Response
```json
{
  "code": "0",
  "message": "success",
  "body": null
}
```

`테스트 편의를위해 4, 5번 API은 GET 으로처리 되었습니다.`
