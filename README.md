This Kotlin Multiplatform project demonstrates a cross-platform music player implemented for Android, iOS, and Web.
The audio source used in the application is publicly available online from SoundHelix.
All core playback functionalities (Play, Pause, Stop) work consistently across platforms.
On iOS, the seek functionality is fully operational, although the progress slider does not yet visually update in real time.
The Android and Web versions support smooth playback and control interaction, with further improvements planned for synchronization and seek feedback in future updates.
This project demonstrates the use of Kotlin Multiplatform to share core playback logic across different platforms while maintaining native user interfaces.

Android
	1.	Open the project in Android Studio.
	2.	Select the androidApp configuration.
iOS
	1.	Open the project in Xcode via the generated .xcworkspace inside iosApp.
	2.	Select your development team under Signing & Capabilities.
Web
	1.	In the terminal, navigate to the project root.
	2.	Run the following command: ./gradlew jsBrowserDevelopmentRun
  3.	Open your browser and go to: http://localhost:8080/

Andrej Vujnic, andrej.vujnic@gmail.com
