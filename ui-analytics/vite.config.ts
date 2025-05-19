import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3001,
    proxy: {
      '/api/metrics': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        // add X-Forwarded headers so Spring can pick up the real client IP
        headers: {
          'X-Forwarded-Host': 'localhost:3001',
          'X-Forwarded-Proto': 'http'
        }
      },

      '/api/shorten': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
        headers: {
          'X-Forwarded-Host': 'localhost:3001',
          'X-Forwarded-Proto': 'http'
        }
      }
    }
  }
})
