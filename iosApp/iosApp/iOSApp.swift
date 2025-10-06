import SwiftUI
import shared

@main
struct iosAppApp: App {
    private let player = IosPlayerKt.createPlayer(
        url: "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    )

    var body: some Scene {
        WindowGroup {
            ContentView(player: player)
        }
    }
}
