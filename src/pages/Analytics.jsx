import React from 'react';
import { motion } from 'framer-motion';
import StatCard from '../components/StatCard';

const Analytics = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-6xl mx-auto">
        <motion.h1
          className="text-4xl font-bold mb-2 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        >
          📈 Analytics Dashboard
        </motion.h1>
        <p className="text-gray-400 mb-8">Deep dive into your performance metrics</p>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
          <StatCard title="Weekly Avg" value="6.5h" icon="📊" />
          <StatCard title="Productivity" value="92%" icon="⚡" />
          <StatCard title="Tasks Complete" value="87%" icon="✅" />
          <StatCard title="Best Day" value="Monday" icon="📅" />
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            className="bg-gradient-to-br from-slate-800 to-slate-900 border border-cyan-400/30 rounded-xl p-6"
          >
            <h3 className="text-xl font-bold text-white mb-4">📅 Weekly Overview</h3>
            <div className="space-y-3">
              {['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'].map((day, idx) => (
                <div key={day} className="flex items-center gap-3">
                  <p className="w-12 text-cyan-400 font-bold">{day}</p>
                  <div className="flex-1 bg-slate-700 rounded-full h-6 overflow-hidden">
                    <motion.div
                      className="bg-gradient-to-r from-cyan-500 to-blue-500 h-full"
                      initial={{ width: 0 }}
                      animate={{ width: `${(idx + 1) * 12}%` }}
                      transition={{ delay: idx * 0.1, duration: 0.5 }}
                    />
                  </div>
                  <p className="text-gray-400 w-12 text-right">{(idx + 1) * 1.2}h</p>
                </div>
              ))}
            </div>
          </motion.div>

          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.2 }}
            className="bg-gradient-to-br from-slate-800 to-slate-900 border border-cyan-400/30 rounded-xl p-6"
          >
            <h3 className="text-xl font-bold text-white mb-4">🎯 Task Distribution</h3>
            <div className="space-y-4">
              {[
                { label: 'UPSC Studies', value: 35, color: 'from-blue-500 to-cyan-500' },
                { label: 'Coding', value: 30, color: 'from-purple-500 to-pink-500' },
                { label: 'Fitness', value: 20, color: 'from-orange-500 to-red-500' },
                { label: 'Focus/Break', value: 15, color: 'from-yellow-500 to-orange-500' },
              ].map((task) => (
                <div key={task.label}>
                  <div className="flex justify-between mb-2">
                    <p className="text-gray-300">{task.label}</p>
                    <p className="text-cyan-400 font-bold">{task.value}%</p>
                  </div>
                  <div className="bg-slate-700 rounded-full h-4 overflow-hidden">
                    <motion.div
                      className={`h-full bg-gradient-to-r ${task.color}`}
                      initial={{ width: 0 }}
                      animate={{ width: `${task.value}%` }}
                      transition={{ duration: 0.8, ease: 'easeOut' }}
                    />
                  </div>
                </div>
              ))}
            </div>
          </motion.div>
        </div>
      </div>
    </div>
  );
};

export default Analytics;