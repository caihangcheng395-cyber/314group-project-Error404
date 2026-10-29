import { useEffect, useMemo, useState } from 'react';
import { getCategory, listCategories } from '../services/categoryApi.js';
import UpdateIDPCategoryPage from './UpdateIDPCategoryPage.jsx';

const icons = ['◫', '◩', '▧', '◈', '▤', '▦', '◉', '▥', '◇', '▣', '▨', '⬡'];

export default function ViewIDPCategoryPage() {
  const [categories, setCategories] = useState([]);
  const [query, setQuery] = useState('');
  const [selected, setSelected] = useState(null);
  const [editing, setEditing] = useState(false);
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    let active = true;
    listCategories()
      .then((items) => { if (active) setCategories(items); })
      .catch((err) => { if (active) setError(err.message); })
      .finally(() => { if (active) setLoading(false); });
    return () => { active = false; };
  }, []);

  const visible = useMemo(() => categories.filter((item) =>
    `${item.name} ${item.description ?? ''}`.toLowerCase().includes(query.toLowerCase())
  ), [categories, query]);

  async function openCategory(id) {
    setError('');
    setSuccess('');
    setEditing(false);
    try {
      setSelected(await getCategory(id));
    } catch (err) {
      setError(err.message);
    }
  }

  function handleSaved(updated) {
    setCategories((previous) => previous.map((item) => item.id === updated.id ? updated : item));
    setSelected(updated);
    setEditing(false);
    setSuccess(`Category "${updated.name}" updated successfully.`);
  }

  return (
    <>
      <section className="page-heading">
        <div>
          <p className="eyebrow">PLATFORM MANAGEMENT <span className="divider">/</span> CATEGORY DIRECTORY</p>
          <h1>Explore design categories<span className="title-period">.</span></h1>
          <p className="intro">A shared foundation for discovering, organising and managing interior design projects.</p>
        </div>
        <span className="heading-tag">CSIT314 · VIEW IDP CATEGORY</span>
      </section>

      <section className="metrics" aria-label="System overview">
        <div className="metric"><span>AVAILABLE CATEGORIES</span><strong>{categories.length}</strong><small>Loaded from H2 database</small></div>
        <div className="metric"><span>ACTIVE CATEGORIES</span><strong>{categories.filter((c) => c.active).length}</strong><small>Ready for project tagging</small></div>
        <div className="metric"><span>BACKEND CONNECTION</span><strong className={error ? 'connection-bad' : 'connection-good'}>{error ? 'Error' : loading ? 'Loading' : 'Online'}</strong><small>Spring Boot · /api/categories</small></div>
      </section>

      <section className="content-section">
        <div className="section-topline">
          <div><p className="eyebrow">CATEGORY LIBRARY</p><h2>Browse the collection</h2></div>
          <label className="search-box"><span aria-hidden="true">⌕</span><input type="search" placeholder="Search categories..." aria-label="Search categories" value={query} onChange={(event) => setQuery(event.target.value)} /></label>
        </div>
        {success && <p className="notice success" role="status">{success}</p>}
        {loading && <p className="notice">Loading categories from the backend…</p>}
        {error && <p className="notice error" role="alert">{error} Start the backend first, then refresh.</p>}
        {!loading && !error && visible.length === 0 && <p className="notice">No matching categories.</p>}
        <div className="card-grid">
          {visible.map((item, index) => (
            <button key={item.id} type="button" className="category-card" onClick={() => openCategory(item.id)}>
              <span className="card-symbol">{icons[index % icons.length]}</span>
              <span className="card-meta">CATEGORY {String(index + 1).padStart(2, '0')} <span className="arrow">↗</span></span>
              <strong>{item.name}</strong>
              <span className="card-description">{item.description}</span>
              <span className="card-footer"><span className="active-dot" />{item.active ? 'Active' : 'Inactive'}<span>VIEW DETAILS →</span></span>
            </button>
          ))}
        </div>
      </section>

      {selected && !editing && (
        <div className="modal-backdrop" onClick={() => setSelected(null)} role="presentation">
          <section className="detail-modal" role="dialog" aria-modal="true" aria-labelledby="detail-title" onClick={(event) => event.stopPropagation()}>
            <button type="button" className="close-button" onClick={() => setSelected(null)} aria-label="Close">×</button>
            <p className="eyebrow">IDP CATEGORY DETAILS</p>
            <h2 id="detail-title">{selected.name}</h2>
            <p>{selected.description}</p>
            <dl><dt>Category ID</dt><dd>{selected.id}</dd><dt>Status</dt><dd>{selected.active ? 'Active' : 'Inactive'}</dd></dl>
            <div className="form-actions">
              <button type="button" className="primary-button" onClick={() => setEditing(true)}>Edit Category</button>
            </div>
            <p className="modal-footnote">Data retrieved via GET /api/categories/{selected.id}</p>
          </section>
        </div>
      )}
      {selected && editing && (
        <UpdateIDPCategoryPage
          category={selected}
          onCancel={() => setEditing(false)}
          onSaved={handleSaved}
        />
      )}
    </>
  );
}
