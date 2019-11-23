Usage: java -jar user-account-service.jar [options]
  Options:
    --port, -p
      Port to run the User Account Service on
      Default: 8080
    --grpc-host, -gh
      IP address/domain of gRPC password service host
      Default: 127.0.0.1
    --grpc-port, -gp
      Port of gRPC password service host
      Default: 50051
    --num-dummy-users, -du
      Number of random dummy users to create on startup (seeded)
      Default: 5
    --usage, -u
      Print usage instructions and exit
      Default: false

Example:
  java -jar user-account-service.jar -p 9090 -du 10 -gh 127.0.0.1 -gp 8080

- ALL arguments are optional, and can be specified in any order
- There is no validation on arguments aside from what JCommander does by default

Github URL: https://github.com/Ronan-H/user-account-service
OpenAPI definition: https://app.swaggerhub.com/apis/Ronan-H/user-account-service/1.0.0
(Also included in the repo as swagger.yaml)

test-run.md in this repo shows an example of request/response for each resource.
This doesn't include any non 200 response examples, or any XML requests/responses but they should work too.