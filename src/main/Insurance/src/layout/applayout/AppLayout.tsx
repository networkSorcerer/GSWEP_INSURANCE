import React, { useState } from "react";
import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Drawer,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  CssBaseline,
  Box,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import HomeIcon from "@mui/icons-material/Home";
import SettingsIcon from "@mui/icons-material/Settings";
import { Outlet } from "react-router-dom";

const drawerWidth = 240;

const AppLayout = ({ children }: { children?: React.ReactNode }) => {
  const [open, setOpen] = useState(false);

  const toggleDrawer = () => {
    setOpen(!open);
  };
  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />

      {/* 상단 헤더 */}
      <AppBar
        position="fixed"
        sx={{ zIndex: (theme) => theme.zIndex.drawer + 1, bgcolor: "#FF7043" }} // 오렌지 톤 (#FF7043)
      >
        <Toolbar>
          <IconButton
            color="inherit"
            edge="start"
            onClick={toggleDrawer}
            sx={{ mr: 2 }}
            aria-label="menu"
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div">
            Insurance App
          </Typography>
        </Toolbar>
      </AppBar>

      {/* 좌측 사이드바 */}
      <Drawer
        variant="temporary" // 여기 변경! overlay 방식
        open={open}
        onClose={toggleDrawer} // 밖 클릭 시 닫히도록 꼭 넣어주세요
        ModalProps={{
          keepMounted: true, // 모바일 성능 최적화용
        }}
        sx={{
          "& .MuiDrawer-paper": {
            width: drawerWidth,
            boxSizing: "border-box",
          },
        }}
      >
        {/* 사이드바 내용 */}

        <Toolbar />
        <List>
          <ListItem component="button">
            <ListItemIcon>
              <HomeIcon />
            </ListItemIcon>
            <ListItemText primary="Home" />
          </ListItem>

          <ListItem component="button">
            <ListItemIcon>
              <SettingsIcon />
            </ListItemIcon>
            <ListItemText primary="Settings" />
          </ListItem>
        </List>
      </Drawer>

      {/* 메인 콘텐츠 영역 */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          minHeight: "600px",
        }}
      >
        <Toolbar />
        {children}
        <Outlet />
      </Box>
    </Box>
  );
};

export default AppLayout;
