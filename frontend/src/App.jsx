import Header from './components/Header.jsx';
import ViewIDPCategoryPage from './pages/ViewIDPCategoryPage.jsx';

export default function App() {
  return (
    <div className="app-shell">
      <Header />
      <main className="container"><ViewIDPCategoryPage /></main>
      <footer className="site-footer"><span>INTERIORLINK / CSIT314</span><span>University coursework · Starter architecture, not a finished product</span></footer>
    </div>
  );
}
