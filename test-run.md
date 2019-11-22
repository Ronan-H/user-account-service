Running user account service and gRPC password service with no arguments. Creates 5 dummy users by default.

## GET /users (list all users)

**Response:** 200 OK
```json
[
    {
        "userDetails": {
            "userId": 0,
            "userName": "OrlaOHara",
            "email": "orla.ohara@gmail.com"
        },
        "hashPairRep": {
            "hashedPassword": "Dsui3M+niSAn+Tr4FIINiASIKp55aRj0D6qy7miqy0E=",
            "salt": "/sgC3xbdI+wuA7hEG69RETWYKvwF491uMjhPhYxkjtU="
        }
    },
    {
        "userDetails": {
            "userId": 1,
            "userName": "FrankHenderson",
            "email": "frank.henderson@gmit.ie"
        },
        "hashPairRep": {
            "hashedPassword": "n0xZL1YSJkwiZePFyxF98TR6p2CB49ThfViFXOdBAAI=",
            "salt": "CW2qN7OffXg3/oHIVEHrLxtMtq9M0jPB49WDdH0mIYw="
        }
    },
    {
        "userDetails": {
            "userId": 2,
            "userName": "LauraHenderson",
            "email": "laura.henderson@yahoo.com"
        },
        "hashPairRep": {
            "hashedPassword": "RB5fmPErtyFvEOOTeVkXuDxMFui1SDLqJq+iblHNizQ=",
            "salt": "X2SxmTHBsEoB6l64QiI/s54lk8yZuO+83zejUJDiy/o="
        }
    },
    {
        "userDetails": {
            "userId": 3,
            "userName": "SeanDaly",
            "email": "sean.daly@yahoo.com"
        },
        "hashPairRep": {
            "hashedPassword": "rEGuslIAMNKBVQPsUrPuBMo7BwLn2Im4+nPy3XNiLgk=",
            "salt": "sc90XF/SRzsiho4aR/JWaXP66s8X71rwcQW8I9T2+dA="
        }
    },
    {
        "userDetails": {
            "userId": 4,
            "userName": "CatherineBurns",
            "email": "catherine.burns@mail.com"
        },
        "hashPairRep": {
            "hashedPassword": "z7MZOKNtiY54ltckX5eRN5jjETl7AW9/6NUBcIl0PVo=",
            "salt": "kLzourv9Uv+fTX6dR+RxyEWFFv5M7XVZivl/ch+qd8k="
        }
    }
]
```

## POST /users (create a new user)

**Request body:**
```json
{
  "userDetails": {
    "userId": 5,
    "userName": "JohnSmith",
    "email": "john.smith@example.com"
  },
  "password": "qwerty12345"
}
```

**Response:** 201 Created

*User created successfully*

## GET /users/5 (get info on a specific user)

**Response:** 200 OK

```json
{
    "userDetails": {
        "userId": 5,
        "userName": "JohnSmith",
        "email": "john.smith@example.com"
    },
    "hashPairRep": {
        "hashedPassword": "jYWD7Fm4TxfY6yL1tswWZhLajtGBSYWcNtz9Rh6AQrE=",
        "salt": "7b2fq9VoXHYlT9hcsdqCJUx0Lqi70/SNQoIl2qgv91M="
    }
}
```

## DELETE /users/5 (delete a user)

**Response:** 200 OK

*User deleted successfully*

## PUT users

**Request body:**
```json
{
  "userDetails": {
    "userId": 0,
    "userName": "UpdatedUser",
    "email": "john.smith@example.com"
  },
  "password": "password123"
}
```

**Response:** 200 OK

*User updated successfully*

## POST /login (login a user)

**Request body:**
```json
{
    "userName": "SeanDaly",
    "password": "ABCDE1"
}
```

**Response:** 200 OK

*Login successful*