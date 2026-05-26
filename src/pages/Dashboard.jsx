import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import StatCard from '../components/StatCard';
import ProgressCircle from '../components/ProgressCircle';

const Dashboard = () => {
  const [stats, setStats] = useState({
    totalTasks: 45,
    completedTasks: 32,
    streakDays: 12,
    focusHours: 18.5,
    upscProgress: 65,
    codingProgress: 78,
    fitnessProgress: 55,
    xpPoints: 2450,
  });

  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.1,
        delayChildren: 0.2,
      },
    },
  };

  const itemVariants = {
    hidden: { opacity: 0, y: 20 },
    visible: {
      opacity: 1,
      y: 0,
      transition: { duration: 0.5 },
    },
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6">
      <motion.div
        className="max-w-7xl mx-auto"
        variants={containerVariants}
        initial="hidden"
        animate="visible"
      >
        {/* Header */}
        <motion.div variants={itemVariants} className="mb-8">
          <h1 className="text-4xl font-bold mb-2 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent">
            Welcome Back! 🚀
          </h1>
          <p className="text-gray-400">Your Discipline. Our Technology. Your Success.</p>
        </motion.div>

        {/* Stats Grid */}
        <motion.div
          className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8"
          variants={containerVariants}
        >
          <motion.div variants={itemVariants}>
            <StatCard
              title="Total Tasks"
              value={stats.totalTasks}
              icon="✅"
              color="cyan"
              progress={Math.round((stats.completedTasks / stats.totalTasks) * 100)}
            />
          </motion.div>
          <motion.div variants={itemVariants}>
            <StatCard
              title="Streak Days"
              value={stats.streakDays}
              icon="🔥"
              color="orange"
            />
          </motion.div>
          <motion.div variants={itemVariants}>
            <StatCard
              title="Focus Hours"
              value={stats.focusHours}
              icon="⏱️"
              color="purple"
            />
          </motion.div>
          <motion.div variants={itemVariants}>
            <StatCard
              title="XP Points"
              value={stats.xpPoints}
              icon="⭐"
              color="yellow"
            />
          </motion.div>
        </motion.div>

        {/* Progress Section */}
        <motion.div variants={itemVariants} className="mb-8">
          <h2 className="text-2xl font-bold mb-6 text-white">Progress Overview</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            <ProgressCircle
              label="UPSC Prep"
              percentage={stats.upscProgress}
              icon="📚"
            />
            <ProgressCircle
              label="Coding Skills"
              percentage={stats.codingProgress}
              icon="💻"
            />
            <ProgressCircle
              label="Fitness Goal"
              percentage={stats.fitnessProgress}
              icon="💪"
            />
            <ProgressCircle
              label="Daily Tasks"
              percentage={Math.round((stats.completedTasks / stats.totalTasks) * 100)}
              icon="✅"
            />
          </div>
        </motion.div>

        {/* Quick Actions */}
        <motion.div variants={itemVariants}>
          <h2 className="text-2xl font-bold mb-6 text-white">Quick Actions</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <motion.button
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
              className="bg-gradient-to-r from-cyan-500 to-blue-500 p-6 rounded-xl font-semibold text-white hover:shadow-lg hover:shadow-cyan-500/50 transition-all"
            >
              📝 Start Daily Task
            </motion.button>
            <motion.button
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
              className="bg-gradient-to-r from-purple-500 to-pink-500 p-6 rounded-xl font-semibold text-white hover:shadow-lg hover:shadow-purple-500/50 transition-all"
            >
              🎯 Start Focus Mode
            </motion.button>
            <motion.button
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
              className="bg-gradient-to-r from-orange-500 to-red-500 p-6 rounded-xl font-semibold text-white hover:shadow-lg hover:shadow-orange-500/50 transition-all"
            >
              🤖 Ask Vayu AI
            </motion.button>
          </div>
        </motion.div>
      </motion.div>
    </div>
  );
};

export default Dashboard;
