## `fcmpush` - A Firebase Cloud Messaging utility

`fcmpush` is simple CLI tool for sending push notifications over Firebase Cloud Messaging

> This documentation is work in progress

## Usage

```shell
fcmpush \                        
--auth-service-account ~/MyProjects/my-sampleproject-0c422-firebase-adminsdk-mhase-6f3c19253f.json \
--token eiLVTfHUTjKpQkiq2eC1po:APA91bGj9y7De6sl-5QRRwnCnkwrcti62m6jl4fsY__fXP1wMhQ-9lQEN-mBqwqWHEq3q73Tuqtqmowtl-uPNf0Svr2_2DDIKvF5A712E3gjYCeymtafB78 \
--title 'Hello' \
--body 'Test description' \
--data '{"foo": "bar"}'
```

## Installation

> I am currently working on a convenience script that installs the 
> `fcmpush` utility in a single shot.