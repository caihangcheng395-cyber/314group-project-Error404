export default function Header() {
  return (
    <header className="topbar">
      <a className="brand" href="/" aria-label="InteriorLink home">
        <span className="brand-icon">I<span>.</span></span>
        <span>Interior<span className="brand-accent">Link</span></span>
      </a>
      <div className="topbar-right">
        <span className="status-pill"><span className="status-dot" /> COURSE PROJECT STARTER</span>
        <span className="avatar">CS</span>
      </div>
    </header>
  );
}
