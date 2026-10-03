import os
import sys
import time
from http.server import ThreadingHTTPServer, SimpleHTTPRequestHandler

class ApkDownloadHandler(SimpleHTTPRequestHandler):
    def end_headers(self):
        if self.path.endswith('.apk') or self.path == '/LifeSim.apk':
            self.send_header('Content-Type', 'application/vnd.android.package-archive')
            self.send_header('Content-Disposition', 'attachment; filename="LifeSim.apk"')
            self.send_header('Cache-Control', 'no-cache, no-store, must-revalidate')
        super().end_headers()

    def do_GET(self):
        if self.path == '/' or self.path == '/index.html':
            self.send_response(200)
            self.send_header('Content-Type', 'text/html; charset=utf-8')
            self.end_headers()
            html = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Download LifeSim APK</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background: linear-gradient(135deg, #0f172a 0%, #1e293b 100%);
            color: #ffffff;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            padding: 20px;
            box-sizing: border-box;
            text-align: center;
        }
        .card {
            background: rgba(30, 41, 59, 0.85);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 24px;
            padding: 32px 24px;
            max-width: 420px;
            width: 100%;
            box-shadow: 0 20px 40px rgba(0,0,0,0.5);
            backdrop-filter: blur(10px);
        }
        .icon {
            font-size: 64px;
            margin-bottom: 12px;
            display: inline-block;
        }
        h1 {
            font-size: 26px;
            margin: 0 0 8px 0;
            font-weight: 800;
        }
        .badge {
            background: rgba(16, 185, 129, 0.2);
            color: #34d399;
            padding: 4px 12px;
            border-radius: 999px;
            font-size: 13px;
            font-weight: 700;
            display: inline-block;
            margin-bottom: 16px;
        }
        p {
            color: #94a3b8;
            font-size: 14px;
            line-height: 1.5;
            margin-bottom: 24px;
        }
        .btn {
            display: block;
            background: linear-gradient(135deg, #059669 0%, #10b981 100%);
            color: #ffffff;
            font-weight: 700;
            font-size: 16px;
            padding: 16px 24px;
            border-radius: 14px;
            text-decoration: none;
            box-shadow: 0 10px 20px rgba(16, 185, 129, 0.3);
            transition: transform 0.1s ease;
        }
        .btn:active {
            transform: scale(0.98);
        }
        .meta {
            margin-top: 20px;
            font-size: 12px;
            color: #64748b;
        }
    </style>
</head>
<body>
    <div class="card">
        <div class="icon">⏳</div>
        <h1>LifeSim Mobile</h1>
        <div class="badge">v2.0 (Latest Release)</div>
        <p>The Smartphone & Global Wealth Era update is ready. Tap below to download the APK directly to your Android device.</p>
        <a href="/LifeSim.apk" class="btn" download="LifeSim.apk">⬇️ Download LifeSim.apk (23 MB)</a>
        <div class="meta">Compatible with Android 7.0+ • Direct APK Download</div>
    </div>
</body>
</html>"""
            self.wfile.write(html.encode('utf-8'))
            return
        return super().do_GET()

if __name__ == '__main__':
    while True:
        try:
            server_address = ('0.0.0.0', 3000)
            httpd = ThreadingHTTPServer(server_address, ApkDownloadHandler)
            print("Serving APK on port 3000 with ThreadingHTTPServer...")
            httpd.serve_forever()
        except Exception as e:
            print("Server exception:", e)
            time.sleep(1)
