import SwiftUI

@main
struct BunnyKingApp: App {
    var body: some Scene { WindowGroup { ContentView() } }
}

struct ContentView: View {
    @AppStorage("lang") private var lang = "fi"
    @AppStorage("tile0") private var tile0 = "data"
    @AppStorage("tile1") private var tile1 = "battle"
    @AppStorage("tile2") private var tile2 = "upgrades"
    @AppStorage("tile3") private var tile3 = "forum"
    @State private var screen = "home"
    @State private var editing = false
    @State private var selectedSlot = 0
    private let options = ["data","battle","reports","upgrades","forum","calculator","sources","settings"]
    private var fi: Bool { lang == "fi" }
    private func name(_ id: String) -> String {
        let finnish = ["home":"🏠 Koti","data":"📊 Pelaajat ja ranking","battle":"⚔️ Taistelu","reports":"📋 Taisteluraportit","upgrades":"🔧 Kehitys","forum":"💬 Foorumi","calculator":"🧮 Laskurit","sources":"🌐 Tietolähteet","settings":"⚙️ Asetukset","more":"••• Lisää"]
        let english = ["home":"🏠 Home","data":"📊 Players and ranking","battle":"⚔️ Battle","reports":"📋 Battle reports","upgrades":"🔧 Upgrades","forum":"💬 Forum","calculator":"🧮 Calculators","sources":"🌐 Data sources","settings":"⚙️ Settings","more":"••• More"]
        return (fi ? finnish : english)[id] ?? id
    }
    private var tiles: [String] { [tile0,tile1,tile2,tile3] }
    private func setTile(_ i: Int, _ value: String) {
        switch i { case 0:tile0=value;case 1:tile1=value;case 2:tile2=value;default:tile3=value }
    }
    var body: some View {
        VStack(spacing:0) {
            Text("👑 BUNNY KING").font(.largeTitle.bold()).foregroundStyle(.yellow).padding(.top,24)
            Text("WHITEOUT SURVIVAL").font(.caption).foregroundStyle(.cyan)
            HStack {
                Text(fi ? "Kieli" : "Language")
                Picker("Language", selection:$lang) {
                    Text("Suomi").tag("fi")
                    Text("English").tag("en")
                }.pickerStyle(.menu)
            }.padding(.horizontal)
            ScrollView {
                if screen == "home" {
                    Text(fi ? "Omat pikavalinnat" : "My shortcuts").font(.title2.bold()).padding()
                    Text(fi ? "Muokkaa pitkällä painalluksella" : "Long-press to customize").font(.caption)
                    LazyVGrid(columns:[GridItem(.flexible()),GridItem(.flexible())],spacing:12) {
                        ForEach(0..<4,id:\.self) { i in
                            Button { screen=tiles[i] } label: {
                                Text(name(tiles[i])).font(.headline).frame(maxWidth:.infinity,minHeight:125)
                                    .background(Color(red:0.10,green:0.25,blue:0.40),in:RoundedRectangle(cornerRadius:18))
                            }.simultaneousGesture(LongPressGesture().onEnded { _ in selectedSlot=i;editing=true })
                        }
                    }.padding()
                } else if screen == "more" {
                    ForEach(options,id:\.self) { id in
                        Button(name(id)) { screen=id }.frame(maxWidth:.infinity,alignment:.leading).padding()
                    }
                } else {
                    Text(name(screen)).font(.title.bold()).padding(.top,32)
                    Text(fi ? "Esikatselu: palvelutoiminnot eivät ole vielä käytössä." : "Preview: backend features are not yet connected.")
                        .foregroundStyle(.secondary).padding()
                }
            }
            HStack {
                ForEach(["home","data","battle","more"],id:\.self) { id in
                    Button(name(id)) { screen=id }.font(.caption).frame(maxWidth:.infinity).padding(.vertical,16)
                }
            }.background(Color(red:0.08,green:0.15,blue:0.23))
        }.foregroundStyle(.white).background(Color(red:0.05,green:0.09,blue:0.15).ignoresSafeArea())
        .confirmationDialog(fi ? "Valitse pikavalinta" : "Choose shortcut",isPresented:$editing) {
            ForEach(options,id:\.self) { id in Button(name(id)) { setTile(selectedSlot,id) } }
        }
    }
}
