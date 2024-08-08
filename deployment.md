## Release APK

### Key

```
keytool -genkey -v -keystore libreforge_tenant_android_test.keystore -alias libreforge_test -keyalg RSA -keysize 2048 -validity 10000
```

### Generate

```
cd android

./gradlew assembleRelease
```

### Artifact

See `android/app/build/outputs/apk/app-release.apk`
