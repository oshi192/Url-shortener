// ui-analytics/src/main.tsx

import React from 'react'
import { createRoot } from 'react-dom/client'
import App from './App'
import 'leaflet/dist/leaflet.css'
import './style.css'

const container = document.getElementById('root')
if (container) createRoot(container).render(<App />)
