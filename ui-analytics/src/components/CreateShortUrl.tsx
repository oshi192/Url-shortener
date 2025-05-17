import React, { useState } from 'react'

export default function CreateShortUrl() {
  const [longUrl, setLongUrl] = useState('')
  const [customAlias, setCustomAlias] = useState('')
  const [result, setResult] = useState<{ alias: string } | null>(null)
  const [error, setError] = useState<string | null>(null)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    setResult(null)

    try {
      const res = await fetch('http://localhost:8080/api/shorten', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ longUrl, customAlias: customAlias || undefined })
      })
      if (!res.ok) throw new Error(`Server returned ${res.status}`)
      const json = await res.json()
      setResult(json)
    } catch (err: any) {
      setError(err.message)
    }
  }

  return (
    <div style={{ marginBottom: 32 }}>
      <h2>Create a Short URL</h2>
      <form onSubmit={handleSubmit} style={{ display: 'flex', gap: '8px', flexWrap: 'wrap' }}>
        <input
          type="url"
          placeholder="Enter long URL"
          value={longUrl}
          onChange={e => setLongUrl(e.target.value)}
          required
          style={{ flex: '1 1 300px' }}
        />
        <input
          type="text"
          placeholder="Custom alias (optional)"
          value={customAlias}
          onChange={e => setCustomAlias(e.target.value)}
          style={{ flex: '0 1 150px' }}
        />
        <button type="submit">Shorten</button>
      </form>

      {error && <p style={{ color: 'red' }}>Error: {error}</p>}
      {result && (
        <p>
          Your short URL is:{' '}
          <a href={`http://localhost:8080/${result.alias}`} target="_blank" rel="noopener noreferrer">
            http://localhost:8080/{result.alias}
          </a>
        </p>
      )}
    </div>
  )
}
