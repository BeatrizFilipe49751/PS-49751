import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import CVPage from "./pages/CvPage";
import RegisterPage from "./pages/RegisterPage";
import ImportPage from "./pages/ImportPage";
import ExportPage from "./pages/ExportPage";
import Navbar from "./components/Navbar";
import { AuthProvider } from "./components/AuthContext";
import PrivateRoute from "./components/PrivateRoute";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Navbar />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />

                    {/* Protected Routes */}
                    <Route
                        path="/cv"
                        element={
                            <PrivateRoute>
                                <CVPage />
                            </PrivateRoute>
                        }
                    />
                    <Route
                        path="/import"
                        element={
                            <PrivateRoute>
                                <ImportPage />
                            </PrivateRoute>
                        }
                    />
                    <Route
                        path="/export"
                        element={
                            <PrivateRoute>
                                <ExportPage />
                            </PrivateRoute>
                        }
                    />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;