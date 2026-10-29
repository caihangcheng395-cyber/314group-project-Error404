/** Single API adapter: do not scatter fetch URLs throughout React components. */
async function request(path, options = {}) {
  const { headers = {}, ...rest } = options;
  const response = await fetch(path, {
    ...rest,
    headers: { Accept: 'application/json', ...headers },
  });
  if (!response.ok) {
    if (response.status === 400) throw new Error('Please check the category details.');
    if (response.status === 404) throw new Error('Category not found.');
    if (response.status === 409) throw new Error('A category with this name already exists.');
    throw new Error(`Request failed (${response.status}). Please check the backend.`);
  }
  return response.json();
}

export function listCategories() {
  return request('/api/categories');
}

export function getCategory(categoryId) {
  return request(`/api/categories/${encodeURIComponent(categoryId)}`);
}

/** User Story #41 — updates a persisted category. */
export function updateCategory(categoryId, category) {
  return request(`/api/categories/${encodeURIComponent(categoryId)}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(category),
  });
}
