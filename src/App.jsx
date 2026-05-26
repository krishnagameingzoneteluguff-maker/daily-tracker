import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navigation from './components/Navigation';
import Dashboard from './pages/Dashboard';
import DailyTimetable from './pages/DailyTimetable';
import UPSCTracker from './pages/UPSCTracker';
import CodingTracker from './pages/CodingTracker';
import FocusMode from './pages/FocusMode';
import FitnessTracker from './pages/FitnessTracker';
import StreakXP from './pages/StreakXP';
import Analytics from './pages/Analytics';
import VayuAI from './pages/VayuAI';
import Notifications from './pages/Notifications';
import UserProfile from './pages/UserProfile';
import DesignSystem from './pages/DesignSystem';

function App() {
  return (
    <Router>
      <div className="bg-gradient-to-br from-slate-900 via-blue-900 to-black min-h-screen text-white">
        <Navigation />
        <main className="pt-20">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/timetable" element={<DailyTimetable />} />
            <Route path="/upsc" element={<UPSCTracker />} />
            <Route path="/coding" element={<CodingTracker />} />
            <Route path="/focus" element={<FocusMode />} />
            <Route path="/fitness" element={<FitnessTracker />} />
            <Route path="/streak" element={<StreakXP />} />
            <Route path="/analytics" element={<Analytics />} />
            <Route path="/ai" element={<VayuAI />} />
            <Route path="/notifications" element={<Notifications />} />
            <Route path="/profile" element={<UserProfile />} />
            <Route path="/design" element={<DesignSystem />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
