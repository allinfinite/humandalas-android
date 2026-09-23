# Humandalas for Android

This is a clean native Android rebuild of the recoverable core Humandalas experience. It is designed to work offline and contains:

- a 46-card browser organized into Connection, Intention, Cultivation, Offering, and Anchor;
- card details and Android text sharing;
- a five-step guided Humandala that chooses one card from each stage; and
- a completion reflection.

The app declares no runtime permissions and has no networking, analytics, advertising, push, account, billing, or cloud SDKs.

## License

Humandalas is free software licensed under the GNU General Public License v3.0 or later (`GPL-3.0-or-later`). See [`LICENSE`](LICENSE) for the complete license terms and [`COPYRIGHT.md`](COPYRIGHT.md) for the material covered by this release.

## Build

Requirements: JDK 17 and Android SDK 36.

```sh
./gradlew clean testDebugUnitTest assembleDebug lintDebug
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Recovered material and exclusions

The app's stage/card structure and text were transcribed from the preserved private Corona/Lua project. The preserved source was read only and was not modified.

No recovered visual, font, audio, or video file is bundled because its ownership and distribution provenance have not yet been documented. No recovered JAR, native library, compiled Lua, signing key, commercial helper library, cloud credential, analytics configuration, push configuration, purchase code, or remote-download code is present. The mandala artwork in this rebuild is drawn at runtime from simple geometric shapes.

Camera capture is intentionally omitted. The recovered active deck had already disabled its Photo card, and the usable guided practice does not require device access. This keeps the rebuild permission-free while provenance and product scope are reviewed.

Daniel Levy has authorized release of the source code, runtime-drawn geometric artwork, and recovered card text in this repository under `GPL-3.0-or-later`. No excluded recovered media or third-party proprietary component is covered or distributed by this repository.

## Release status

Version 2.0.1 is published as GPL-3.0-or-later source with screenshots and F-Droid metadata. Release APKs remain unsigned in the source tree so F-Droid can build and sign accepted releases independently.
