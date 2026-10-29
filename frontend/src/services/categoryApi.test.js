import test from 'node:test';
import assert from 'node:assert/strict';
import { listCategories, getCategory, updateCategory } from './categoryApi.js';

test('listCategories requests the agreed backend route', async (t) => {
  const previous = globalThis.fetch;
  t.after(() => { globalThis.fetch = previous; });
  globalThis.fetch = async (url) => {
    assert.equal(url, '/api/categories');
    return { ok: true, json: async () => [{ id: 1, name: 'Kitchen' }] };
  };
  assert.deepEqual(await listCategories(), [{ id: 1, name: 'Kitchen' }]);
});

test('getCategory uses category ID in its route', async (t) => {
  const previous = globalThis.fetch;
  t.after(() => { globalThis.fetch = previous; });
  globalThis.fetch = async (url) => {
    assert.equal(url, '/api/categories/3');
    return { ok: true, json: async () => ({ id: 3, name: 'Bedroom' }) };
  };
  assert.equal((await getCategory(3)).name, 'Bedroom');
});


test('updateCategory sends PUT with JSON and returns the saved category', async (t) => {
  const previous = globalThis.fetch;
  t.after(() => { globalThis.fetch = previous; });
  const changes = { name: 'Master Bedroom', description: 'Updated', active: false };
  globalThis.fetch = async (url, options) => {
    assert.equal(url, '/api/categories/3');
    assert.equal(options.method, 'PUT');
    assert.equal(options.headers['Content-Type'], 'application/json');
    assert.deepEqual(JSON.parse(options.body), changes);
    return { ok: true, json: async () => ({ id: 3, ...changes }) };
  };
  assert.equal((await updateCategory(3, changes)).name, 'Master Bedroom');
});

test('updateCategory reports duplicate names', async (t) => {
  const previous = globalThis.fetch;
  t.after(() => { globalThis.fetch = previous; });
  globalThis.fetch = async () => ({ ok: false, status: 409 });
  await assert.rejects(updateCategory(3, { name: 'Kitchen', description: '', active: true }),
    /already exists/);
});
