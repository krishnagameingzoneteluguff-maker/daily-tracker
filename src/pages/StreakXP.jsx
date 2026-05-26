import React, { useState } from 'react';
import { motion } from 'framer-motion';
import StatCard from '../components/StatCard';

const StreakXP = () => {
  const [achievements] = useState([
    { title: 'Week Warrior', description: '7 day streak', icon: '🏆', unlocked: true },
    { title: 'Month Master', description: '30 day streak', icon: '👑', unlocked: true },
    { title: 'Century', description: '100 tasks completed', icon: '💯', unlocked: false },
    { title: 'Focus Master', description: '50 focus sessions', icon: '🎯', unlocked: true },
    { title: 'Code Warrior', description: 'Solve 500 problems', icon: '⚔️', unlocked: false },
    { title: 'Fitness Legend', description: 'Complete 100 workouts', icon: '🦸', unlocked: false },
  ]);

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-6xl mx-auto">
        <motion.h1
          className="text-4xl font-bold mb-2 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        >
          🔥 Streak & XP System
        </motion.h1>
        <p className="text-gray-400 mb-8">Track your achievements and unlock rewards</p>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
          <StatCard title="Current Streak" value="12" icon="🔥" />
          <StatCard title="Total XP" value="24500" icon="⭐" />
          <StatCard title="Level" value="18" icon="📈" />
          <StatCard title="Achievements" value="4/6" icon="🏆" />
        </div>

        <h2 className="text-2xl font-bold mb-6 text-white">Achievements Unlocked</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {achievements.map((achievement, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: index * 0.05 }}
              className={`border rounded-lg p-6 ${
                achievement.unlocked
                  ? 'bg-gradient-to-r from-yellow-500/10 to-orange-500/10 border-yellow-400/50'
                  : 'bg-slate-800/50 border-slate-600/50 opacity-60'
              }`}
            >
              <div className="text-4xl mb-3">{achievement.icon}</div>
              <p className="text-white font-bold text-lg">{achievement.title}</p>
              <p className="text-gray-400 text-sm">{achievement.description}</p>
            </motion.div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default StreakXP;