// ui-analytics/src/App.tsx
import React from 'react'
import CreateShortUrl from './components/CreateShortUrl'
import ClickRateChart from './components/ClickRateChart'
import GeoHeatmap from './components/GeoHeatmap'
import TopUrls from './components/TopUrls'

export default function App() {
  return (
    <div className="container">
      <div className="card">
        <h1>URL Shortener & Analytics</h1>
        <CreateShortUrl />
        <hr />
        <h2>Analytics</h2>
        <ClickRateChart />
        <GeoHeatmap />
        <TopUrls />
      </div>
    </div>
  )
}
