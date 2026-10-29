import test from 'node:test';
import assert from 'node:assert/strict';
import { listCategories, getCategory } from './categoryApi.js';

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
