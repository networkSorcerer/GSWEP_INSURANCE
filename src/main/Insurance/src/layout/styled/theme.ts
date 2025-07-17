// src/theme.ts
import { createTheme } from "@mui/material/styles";
import type { PaletteColorOptions } from "@mui/material"; // ★ 여기 import type 으로 변경

declare module "@mui/material/styles" {
  interface Palette {
    orange: Palette["primary"];
  }
  interface PaletteOptions {
    orange?: PaletteColorOptions;
  }
}

const theme = createTheme({
  palette: {
    orange: {
      main: "#FF7043",
      contrastText: "#fff",
    },
  },
});

export default theme;
