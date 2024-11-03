[문서](http://localhost:8080/docs)

# 준비물
1. 데이터베이스  
  mysql 이 필요합니다. 32769 port  
  MYSQL_ROOT_PASSWORD = zxcvb2  
  MYSQL_DATABASE = ssafy_trip_batch

2. 테이블 생성 
   - resources/schema-mysql.sql 을 실행시켜줍니다.
   - resources/create-mysql 을 실행시켜줍니다.

# 시작 방법
시작 방법은 커맨드 전용과 API 전용이 있습니다.
- 커맨드 전용
  - 커맨드 전용은 jar 파일 실행시 전달되는 프로그램 인수 를 해석하여서 해당되는 서비스만 실행한 후 종료됩니다.
  - 프로그램 인수로 command 를 넣어주고 또 다른 인수는 key=value 형식으로 전달해 주면됩니다.
- API 전용
  - 아무런 프로그램 인수 없이 어플리케이션을 실행합니다.
  - 8080 포트로 webflux 가 실행됩니다.
  - API [문서](http://localhost:8080/docs) 를 참고하면 됩니다. 