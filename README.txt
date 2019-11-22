Usage: java -jar user-account-service.jar [options]
  Options:
    --grpc-host, -h
      IP address/domain of gRPC password service host
      Default: 127.0.0.1
    --grpc-port, -p
      Port of gRPC password service host
      Default: 50051
    --num-dummy-users, -du
      Number of random dummy users to create on startup (seeded)
      Default: 5
    --usage, -u
      Print usage instructions and exit
      Default: false

Github URL: https://github.com/Ronan-H/user-account-service
OpenAPI definition: https://app.swaggerhub.com/apis/Ronan-H/user-account-service/1.0.0
(Also included in the repo as swagger.yaml)

test-run.md in this repo shows a request/response for each resource. This doesn't include any non 200 response examples, but they should work too.