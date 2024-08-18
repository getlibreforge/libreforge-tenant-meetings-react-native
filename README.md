# Tenant Experience

# Development Framework

# Prerequisites

- OpenJDK 17
- NodeJS 20.2.0

# Build & Run

## Step 1: Install Dependencies

To install dependencies, run the following command from the _root_ of your React Native project:

```bash
npm i --legacy-peer-deps
```

## Step 2: Start the Metro Server

Then you will need to start **Metro**, the JavaScript _bundler_ that ships _with_ React Native.

To start Metro, run the following command from the _root_ of your React Native project:

```bash
npm start
```

## Step 3: Start your Application

Let Metro Bundler run in its _own_ terminal. Open a _new_ terminal from the _root_ of your React Native project. Run the following command to start your _Android_ or _iOS_ app:

### For Android

```bash
npm run android
```

# Generate APK

### Generate Key

```
keytool -genkey -v -keystore libreforge_tenant_android_test.keystore -alias libreforge_test -keyalg RSA -keysize 2048 -validity 10000
```

### Generate APK

```
cd android

./gradlew assembleRelease
```

### Artifact

See `android/app/build/outputs/apk/app-release.apk`

# User Guides

- [Admin Guide](./etc/guide-admin.md)
- [Tenant Guide](./etc/guide-tenant.md)

