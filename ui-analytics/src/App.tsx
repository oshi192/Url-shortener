import React from 'react'
import CreateShortUrl from './components/CreateShortUrl'
import ClickRateChart from './components/ClickRateChart'
import GeoHeatmap from './components/GeoHeatmap'
import TopUrls from './components/TopUrls'

export default function App() {
  return (
    <div style={{ padding: 20 }}>
      <h1>URL Shortener & Analytics</h1>

      {/* NEW: URL creation form */}
      <CreateShortUrl />

      <hr style={{ margin: '40px 0' }} />

      {/* Existing analytics widgets */}
      <ClickRateChart />
      <GeoHeatmap />
      <TopUrls />
    </div>
  )
}
