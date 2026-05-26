import React, { useState } from 'react';
import { motion } from 'framer-motion';
import StatCard from '../components/StatCard';
import ProgressCircle from '../components/ProgressCircle';

const UPSC = () => {
  const [subjects] = useState([
    { name: 'History', progress: 72, icon: '📖' },
    { name: 'Geography', progress: 68, icon: '🗺️' },
    { name: 'Polity', progress: 85, icon: '⚖️' },
    { name: 'Economy', progress: 55, icon: '💰' },
    { name: 'Science', progress: 78, icon: '🔬' },
    { name: 'Environment', progress: 62, icon: '🌍' },
  ]);

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-6xl mx-auto">
        <motion.h1
          className="text-4xl font-bold mb-2 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        >
          📚 UPSC Preparation
        </motion.h1>
        <p className="text-gray-400 mb-8">Track your progress across all subjects</p>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <StatCard title="Overall Progress" value="70%" icon="📊" />
          <StatCard title="Topics Completed" value="156" icon="✅" />
          <StatCard title="Mock Tests" value="24" icon="📝" />
        </div>

        <h2 className="text-2xl font-bold mb-6 text-white">Subject Progress</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {subjects.map((subject, index) => (
            <ProgressCircle
              key={index}
              label={subject.name}
              percentage={subject.progress}
              icon={subject.icon}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default UPSC;