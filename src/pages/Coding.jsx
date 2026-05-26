import React, { useState } from 'react';
import { motion } from 'framer-motion';
import StatCard from '../components/StatCard';

const Coding = () => {
  const [languages] = useState([
    { name: 'JavaScript', problems: 245, icon: '🟨', streak: 15 },
    { name: 'Python', problems: 189, icon: '🐍', streak: 12 },
    { name: 'React', problems: 156, icon: '⚛️', streak: 18 },
    { name: 'SQL', problems: 98, icon: '🗄️', streak: 8 },
    { name: 'Data Structures', problems: 203, icon: '📦', streak: 14 },
    { name: 'Algorithms', problems: 167, icon: '🔄', streak: 11 },
  ]);

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-6xl mx-auto">
        <motion.h1
          className="text-4xl font-bold mb-2 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        >
          💻 Coding Progress
        </motion.h1>
        <p className="text-gray-400 mb-8">LeetCode & DSA mastery journey</p>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
          <StatCard title="Problems Solved" value="1258" icon="✅" />
          <StatCard title="Current Streak" value="18" icon="🔥" />
          <StatCard title="Difficulty Hard" value="342" icon="🏔️" />
          <StatCard title="Total Hours" value="245" icon="⏱️" />
        </div>

        <h2 className="text-2xl font-bold mb-6 text-white">Language Wise Performance</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {languages.map((lang, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: index * 0.05 }}
              className="bg-gradient-to-r from-slate-800 to-slate-900 border border-cyan-400/30 rounded-lg p-6 hover:shadow-lg hover:shadow-cyan-500/50 transition-all"
            >
              <div className="flex items-center gap-3 mb-4">
                <span className="text-3xl">{lang.icon}</span>
                <div>
                  <p className="text-cyan-400 font-bold">{lang.name}</p>
                  <p className="text-gray-400 text-sm">🔥 {lang.streak} day streak</p>
                </div>
              </div>
              <p className="text-white text-2xl font-bold">{lang.problems} Problems</p>
            </motion.div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Coding;