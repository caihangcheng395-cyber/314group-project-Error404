/** Single API adapter: do not scatter fetch URLs throughout React components. */
async function request(path) {
  const response = await fetch(path, { headers: { Accept: 'application/json' } });
  if (!response.ok) {
    throw new Error(`Request failed (${response.status}). Is the backend running?`);
  }
  return response.json();
}

export function listCategories() {
  return request('/api/categories');
}

export function getCategory(categoryId) {
  return request(`/api/categories/${encodeURIComponent(categoryId)}`);
}
