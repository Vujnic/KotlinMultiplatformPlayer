import SwiftUI
import shared

struct ContentView: View {
    @State private var state = PlayerState(isPlaying: false, currentPositionMs: 0, durationMs: 1)
    @State private var isDragging = false
    @State private var position: Double = 0
    @State private var timer: Timer? = nil

    let player: PlayerController

    var body: some View {
        VStack(spacing: 20) {
            Text("🎧 Kotlin Multiplatform Player")
                .font(.headline)
                .padding(.top, 40)

            // 🎚️ Slider
            Slider(
                value: Binding(
                    get: { position },
                    set: { newValue in
                        position = newValue
                        isDragging = true
                    }
                ),
                in: 0...Double(state.durationMs)
            ) { editing in
                if !editing {
                    player.seek(positionMs: Int64(position))
                    isDragging = false
                }
            }

            Text("\(Int(state.currentPositionMs / 1000))s / \(Int(state.durationMs / 1000))s")

            HStack(spacing: 20) {
                Button(state.isPlaying ? "⏸ Pause" : "▶️ Play") {
                    if state.isPlaying {
                        player.pause()
                    } else {
                        player.play()
                    }
                    refreshState()
                }
                .buttonStyle(.borderedProminent)

                Button("⏹ Stop") {
                    player.stop()
                    refreshState()
                }
                .buttonStyle(.bordered)
            }

            Spacer()
        }
        .padding()
        .onAppear {
            startTimer()
        }
        .onDisappear {
            stopTimer()
        }
    }

    func startTimer() {
        timer?.invalidate()
        timer = Timer.scheduledTimer(withTimeInterval: 0.5, repeats: true) { _ in
            refreshState()
        }
    }

    func stopTimer() {
        timer?.invalidate()
        timer = nil
    }

    func refreshState() {
        let newState = player.getState()
        self.state = newState
        if !isDragging {
            self.position = Double(newState.currentPositionMs)
        }
    }
}
