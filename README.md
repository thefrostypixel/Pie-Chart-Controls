## Features
### Activation mode
Lets you change when keys navigate the pie chart and when they perform their normal actions such as selecting hotbar slots.
- Simultaneous: Always use keys for both at the same time.
- Hold: Hold a selectable key to navigate the pie chart, use the keys normally otherwise.
- Toggle: Press a selectable key to toggle navigating the pie chart, use the keys normally otherwise.

### Control scheme
Lets you change how the pie chart is navigated and with which keys.
- Vanilla: Press 0 to go back and 1 to 9 to go to those sub-categories.
- Hotkeys: Press a selectable key to go back and the hotkeys for slots 1 to 9 to go to those sub-categories.
- Custom: Press selectable keys to go back and to sub-categories.
- Navigate: Press selectable keys to go back, to highlight the sub-category above or below the highlighted one, and to go to the highlighted sub-category.

## Libraries
### Required
- [Fabric API](https://modrinth.com/mod/fabric-api)

## Building
### `./build`
Pull the latest built version of Frosty Lib if it is placed in the same folder as Pie Chart Controls. Build for all supported versions. Places mod-jars in `out/`.

### `./run [<version>]`
Pull the latest built version of Frosty Lib if it is placed in the same folder as Pie Chart Controls. Build and start a client running the given Minecraft version, or to the default version if none is provided.

### `./ver [<version>]`
Switches to the given Minecraft version, or to the default version if none is provided. The default version should be selected when commiting to git.
