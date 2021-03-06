const electron = require('electron'); // eslint-disable-line
const {app, BrowserWindow} = electron;
let mainWindow;
const path = require('path');
const url = require('url');

function createWindow() {
  mainWindow = new BrowserWindow({width: 800, height: 600});

  const startUrl = process.env.ELECTRON_START_URL || url.format({
    pathname: path.join(__dirname, '/../build/index.html'),
    protocol: 'file:',
    slashes: true,
  });
  mainWindow.loadURL(startUrl);
  if (process.env.ELECTRON_START_URL) {
    mainWindow.webContents.openDevTools();
  }
  mainWindow.on('closed', () => {
    mainWindow = null;
  });
}
app.on('ready', createWindow);

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', () => {
  if (mainWindow === null) {
    createWindow();
  }
});
