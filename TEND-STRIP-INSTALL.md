# Tend-Strip Install Guide (for Rm)

## What this is
Your own build of the phone-control app. Stripped down:
- No Cloudflare/ngrok tunnels (deleted)
- No camera, mic, location, or media permissions
- Tailscale-only: reachable only via your tailnet

## Install steps (your hands, ~5 min)

1. **Download the APK** — I'll send you the link when the build finishes.

2. **Allow the install**
   - Open the APK file
   - Android will block it ("Install unknown apps") → tap Settings → allow your browser/files app to install unknown apps

3. **"Allow restricted settings"** (Samsung requirement for sideloaded apps)
   - Go to Settings → Apps → find the app → tap the 3-dot menu (top right) → "Allow restricted settings"
   - Without this, you can't enable its Accessibility service

4. **Enable the Accessibility service**
   - Settings → Accessibility → Installed apps (or Downloaded apps) → turn ON the app's service
   - Accept the warning (it's your own build, you know what it does)

5. **Start the server**
   - Open the app → start the MCP server
   - It'll show the port it's listening on

6. **Tell me the port** — I'll connect to it via Tailscale (100.114.48.25) and verify control works.

## What I need from you
Just the port number the app shows after you start the server. I handle the rest.
