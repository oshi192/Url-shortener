import React, { useEffect, useState } from 'react'
import { MapContainer, TileLayer, CircleMarker, Popup } from 'react-leaflet'

interface GeoCount { region: string; cnt: number }

export default function GeoHeatmap() {
  const [data, setData] = useState<GeoCount[]>([])

  useEffect(() => {
    fetch('http://localhost:8081/api/metrics/geo')
      .then((r) => r.json())
      .then(setData)
  }, [])

  return (
    <div style={{ width: '100%', height: 400, marginTop: 20 }}>
      <h2>Click Geo-Distribution</h2>
      <MapContainer center={[20, 0]} zoom={2} style={{ height: '100%' }}>
        <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
        {data.map((g) => (
          <CircleMarker
            key={g.region}
            center={[Math.random() * 140 - 70, Math.random() * 360 - 180]} // replace with real geocoding
            radius={Math.sqrt(g.cnt) * 2}
          >
            <Popup>{`${g.region}: ${g.cnt}`}</Popup>
          </CircleMarker>
        ))}
      </MapContainer>
    </div>
  )
}
