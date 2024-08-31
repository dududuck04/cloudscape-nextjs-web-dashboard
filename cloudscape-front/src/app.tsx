// import {
//   HashRouter,
//   BrowserRouter,
//   Routes,
//   Route,
//   Outlet,
// } from "react-router-dom";
// import { USE_BROWSER_ROUTER } from "./common/constants";
// import GlobalHeader from "./components/global-header";
// import DashboardPage from "./pages/dashboard/dashboard-page";
// import ViewItemPage from "./pages/section1/view-item/view-item-page";
// import AllItemsPage from "./pages/section1/all-items/all-items-page";
// import AddItemPage from "./pages/section1/add-item/add-item-page";
// import NotFound from "./pages/not-found";
// import "./styles/app.scss";
//
// export default function App() {
//   const Router = USE_BROWSER_ROUTER ? BrowserRouter : HashRouter;
//
//   return (
//     <div style={{ height: "100%" }}>
//       <Router>
//         <GlobalHeader />
//         <div style={{ height: "56px", backgroundColor: "#000716" }}>&nbsp;</div>
//         <div>
//           <Routes>
//             <Route index path="/" element={<DashboardPage />} />
//             <Route path="/section1" element={<Outlet />}>
//               <Route path="" element={<AllItemsPage />} />
//               <Route path="add" element={<AddItemPage />} />
//               <Route path="items/:itemId" element={<ViewItemPage />} />
//             </Route>
//             <Route path="*" element={<NotFound />} />
//           </Routes>
//         </div>
//       </Router>
//     </div>
//   );
// }

// app/page.tsx
import { useEffect } from 'react';
import { notFound } from 'next/navigation';
import { useRouter } from 'next/router';

export default function AppPage() {
  const router = useRouter();

  useEffect(() => {
    // 모든 경로에 대해 Not Found 처리
    const handleRouteChange = () => {
      notFound();
    };

    // 라우트 변경 시 핸들러를 호출
    router.events.on('routeChangeComplete', handleRouteChange);

    // 컴포넌트 언마운트 시 이벤트 리스너 제거
    return () => {
      router.events.off('routeChangeComplete', handleRouteChange);
    };
  }, [router]);

  // 기본적으로 Not Found를 반환합니다.
  // 이 부분은 실제 라우트 변경이 감지되기 전까지 임시로 표시되는 컴포넌트일 수 있습니다.
  return notFound();
}

