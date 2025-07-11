// // OAuth2Button.tsx
// import React from "react";

// interface OAuth2ButtonProps {
//   onClick?: () => void;
//   href?: string;
//   children: React.ReactNode;
//   style?: React.CSSProperties;
// }

// const OAuth2Button: React.FC<OAuth2ButtonProps> = ({
//   onClick,
//   href,
//   children,
//   style = {},
// }) => {
//   const defaultStyle: React.CSSProperties = {
//     display: "inline-block",
//     width: "100%",
//     padding: "10px",
//     color: "#fff",
//     textAlign: "center",
//     border: "none",
//     borderRadius: "4px",
//     cursor: "pointer",
//     textDecoration: "none",
//     marginTop: "10px",
//     boxSizing: "border-box",
//     transition: "background-color 0.3s, opacity 0.3s",
//   };

//   const combinedStyle = {
//     ...defaultStyle,
//     ...style,
//   };

//   const handleMouseEnter = (e: React.MouseEvent<HTMLElement>) => {
//     (e.target as HTMLElement).style.opacity = "0.7";
//   };

//   const handleMouseLeave = (e: React.MouseEvent<HTMLElement>) => {
//     (e.target as HTMLElement).style.opacity = "1";
//   };

//   if (href) {
//     return (
//       <a
//         href={href}
//         style={combinedStyle}
//         onMouseEnter={handleMouseEnter}
//         onMouseLeave={handleMouseLeave}
//       >
//         {children}
//       </a>
//     );
//   }

//   return (
//     <button
//       onClick={onClick}
//       style={combinedStyle}
//       onMouseEnter={handleMouseEnter}
//       onMouseLeave={handleMouseLeave}
//     >
//       {children}
//     </button>
//   );
// };

// export default OAuth2Button;
