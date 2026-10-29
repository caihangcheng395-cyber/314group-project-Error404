import { useState } from 'react';
import { updateCategory } from '../services/categoryApi.js';

/** UI boundary for User Story #41. Parent provides the category selected in #42. */
export default function UpdateIDPCategoryPage({ category, onCancel, onSaved }) {
  const [name, setName] = useState(category.name);
  const [description, setDescription] = useState(category.description ?? '');
  const [active, setActive] = useState(category.active);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');

  async function handleSubmit(event) {
    event.preventDefault();
    const trimmedName = name.trim();
    if (!trimmedName) {
      setError('Category name is required.');
      return;
    }
    setSaving(true);
    setError('');
    try {
      const updated = await updateCategory(category.id, {
        name: trimmedName,
        description: description.trim(),
        active,
      });
      onSaved(updated);
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  }

  return (
    <div className="modal-backdrop" role="presentation">
      <section className="detail-modal edit-modal" role="dialog" aria-modal="true" aria-labelledby="edit-category-title">
        <button type="button" className="close-button" onClick={onCancel} aria-label="Close edit form" disabled={saving}>×</button>
        <p className="eyebrow">PLATFORM MANAGEMENT / USER STORY 41</p>
        <h2 id="edit-category-title">Edit IDP category</h2>
        <p className="edit-subtitle">Category ID: {category.id}</p>
        <form onSubmit={handleSubmit}>
          <label className="form-field">
            <span>Category name *</span>
            <input required maxLength={100} value={name} onChange={(event) => setName(event.target.value)} disabled={saving} />
          </label>
          <label className="form-field">
            <span>Description</span>
            <textarea maxLength={500} rows={4} value={description} onChange={(event) => setDescription(event.target.value)} disabled={saving} />
          </label>
          <label className="toggle-row">
            <input type="checkbox" checked={active} onChange={(event) => setActive(event.target.checked)} disabled={saving} />
            <span>Active category</span>
          </label>
          {error && <p className="notice error" role="alert">{error}</p>}
          <div className="form-actions">
            <button className="secondary-button" type="button" onClick={onCancel} disabled={saving}>Cancel</button>
            <button className="primary-button" type="submit" disabled={saving}>{saving ? 'Saving…' : 'Save Changes'}</button>
          </div>
        </form>
      </section>
    </div>
  );
}
