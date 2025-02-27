## `fcmpush` - A Firebase Cloud Messaging utility

`fcmpush` is simple CLI tool for sending push notifications over Firebase Cloud Messaging. It supports sending
push notification by providing either Firebase token obtained from the mobile application, topic or a condition.

It supports authentication with service account JSON file, or if you have already authenticated and have the
bearer token and project ID, you can use them too.

## Requirements
* JDK 21 (preferred) or higher
* Intellij IDEA CE (optional)

## Installation

> This section is WIP. I am working on a convenience script that makes the installation process easier for you.

The current installation method is building from the source code. You need to install Oracle JDK or any other
vendor such as Eclipse Temurin.

```shell
git clone git@github.com:anand-bose/fcmpush.git
cd fcmpush
./gradlew :installDist
export PATH=$PATH:$PWD/build/install/fcmpush/bin
```
Now you can invoke the `fcmpush` tool directly within the Terminal window, until you close it. To persist `fcmpush`
command in your shell, please update the `PATH` variable your shell's profile.

| Shell                                            | Profile         |
|--------------------------------------------------|-----------------|
| zsh (Default for macOS, some Linux disributions) | ~/.zprofile     |
| bash (Most Linux distributions)                  | ~/.bash_profile |

Create the profile file if it does not exist. If it exists, do not change the contents of the file, just append the
following line to the end of the file.

```shell
PATH=$PATH:/Users/anandbose/Developer/fcmpush/build/install/fcmpush/bin
```
*Note: Replace `/Users/anandbose/Developer/fcmpush` with absolute path of the project directory in your system.*
## Example use cases

### 1. Send push notification to a device, using service account credentials

```shell
fcmpush \                        
--auth-service-account ~/MyProjects/my-sampleproject-0c422-firebase-adminsdk-mhase-6f3c19253f.json \
--token eiLVTfHUTjKpQkiq2eC1po:APA91bGj9y7De6sl-this_is_an_example_token-9lQEN-mBqwqWHEq3q73Tuqtqmowtl-uPNf0Svr2_2DDIKvF5A712E3gjYCeymtafB78 \
--title 'Hello' \
--body 'This is a notification pushed using fcmpush tool' \
--data '{"foo": "bar"}'
```

### 2. Send push notification to a device, using bearer token credentials

```shell
fcmpush \                        
--auth-token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30 \
--project-id my-sampleproject-0c422 \
--token eiLVTfHUTjKpQkiq2eC1po:APA91bGj9y7De6sl-this_is_an_example_token-9lQEN-mBqwqWHEq3q73Tuqtqmowtl-uPNf0Svr2_2DDIKvF5A712E3gjYCeymtafB78 \
--title 'Hello' \
--body 'This is a notification pushed using fcmpush tool' \
--data '{"foo": "bar"}'
```

### 3. Send push notification to a topic

```shell
fcmpush \                        
--auth-service-account ~/MyProjects/my-sampleproject-0c422-firebase-adminsdk-mhase-6f3c19253f.json \
--topic all_users \
--title 'Hello' \
--body 'This is a notification pushed using fcmpush tool' \
--data '{"foo": "bar"}'
```

### 3. Send push notification to combination of topics using condition

```shell
fcmpush \                        
--auth-service-account ~/MyProjects/my-sampleproject-0c422-firebase-adminsdk-mhase-6f3c19253f.json \
--condition "'dogs' in topics || 'cats' in topics" \
--title 'Hello' \
--body 'This is a notification pushed using fcmpush tool' \
--data '{"foo": "bar"}'
```

## Reference documentation

Here is the list of all supported argument parameters for `fcmpush` tool.

* `--auth-token <token value>`
* `--auth-service-account <path to service account credential json>`
* `--token <firebase token>`
* `--topic <topic name>`
* `--condition <condition>`
* `--project-id <project id>` (for use along with `--auth-token`)
* `--title <notification title>`
* `--body <notification content>`
* `--image <url of the image>` (optional)
* `--data <json payload>` (optional)
* `--print-auth-token` (optional, for use with `--auth-service-account` which prints the bearer token for future reuse)