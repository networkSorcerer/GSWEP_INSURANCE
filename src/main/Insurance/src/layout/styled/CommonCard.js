export const CommonCard = ({ title, children }) => (
  <div
    style={{
      border: "1px solid #ddd",
      borderRadius: "8px",
      padding: "20px",
      marginTop: "20px",
      boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
    }}
  >
    <h3 style={{ marginBottom: "15px", color: "#333" }}>{title}</h3>
    {children}
  </div>
);

