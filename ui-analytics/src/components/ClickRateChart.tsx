import React, { useEffect, useState } from 'react'
import {
  LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts'

export default function ClickRateChart() {
  const [data, setData] = useState<{ time: string; lastMinute: number }[]>([])

  useEffect(() => {
    const fetchRate = async () => {
      const res = await fetch('http://localhost:8081/api/metrics/rate')
      const json = await res.json()
      setData((d) => [...d, { time: new Date().toLocaleTimeString(), lastMinute: json.lastMinute }].slice(-20))
    }
    fetchRate()
    const iv = setInterval(fetchRate, 5000)
    return () => clearInterval(iv)
  }, [])

  return (
    <div style={{ width: '100%', height: 300 }}>
      <h2>Click Rate (last minute)</h2>
      <ResponsiveContainer>
        <LineChart data={data}>
          <CartesianGrid />
          <XAxis dataKey="time" />
          <YAxis />
          <Tooltip />
          <Line type="monotone" dataKey="lastMinute" />
        </LineChart>
      </ResponsiveContainer>
    </div>
  )
}
