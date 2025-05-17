import React, { useEffect, useState } from 'react'
import {
  BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts'

interface TopUrl { alias: string; count: number }

export default function TopUrls() {
  const [data, setData] = useState<TopUrl[]>([])

  useEffect(() => {
    fetch('http://localhost:8081/api/metrics/top')
      .then((r) => r.json())
      .then(setData)
  }, [])

  return (
    <div style={{ width: '100%', height: 300, marginTop: 20 }}>
      <h2>Top URLs</h2>
      <ResponsiveContainer>
        <BarChart data={data}>
          <CartesianGrid />
          <XAxis dataKey="alias" />
          <YAxis />
          <Tooltip />
          <Bar dataKey="count" />
        </BarChart>
      </ResponsiveContainer>
    </div>
  )
}
