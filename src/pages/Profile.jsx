import React, { useState } from 'react';
import { motion } from 'framer-motion';
import StatCard from '../components/StatCard';

const Profile = () => {
  const [user] = useState({
    name: 'Krishna',
    email: 'krishna@vayutech.com',
    level: 18,
    xp: 24500,
    joinedDate: 'January 2024',
    bio: 'Aspiring IAS Officer | Full Stack Developer | Fitness Enthusiast',
  });

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-4xl mx-auto">
        {/* Profile Header */}
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          className="bg-gradient-to-r from-slate-800 to-slate-900 border border-cyan-400/30 rounded-xl p-8 mb-8 text-center"
        >
          <motion.div
            className="w-32 h-32 mx-auto mb-6 rounded-full bg-gradient-to-br from-cyan-400 to-blue-400 flex items-center justify-center text-6xl"
            whileHover={{ scale: 1.1 }}
          >
            👤
          </motion.div>
          <h1 className="text-3xl font-bold text-white mb-2">{user.name}</h1>
          <p className="text-cyan-400 mb-4">{user.email}</p>
          <p className="text-gray-300">{user.bio}</p>
        </motion.div>

        {/* Stats */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
          <StatCard title="Level" value={user.level} icon="📈" />
          <StatCard title="Total XP" value={`${user.xp}`} icon="⭐" />
          <StatCard title="Member Since" value="Jan 2024" icon="📅" />
          <StatCard title="Status" value="Active" icon="✅" />
        </div>

        {/* Settings */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="bg-gradient-to-br from-slate-800 to-slate-900 border border-cyan-400/30 rounded-xl p-6"
        >
          <h2 className="text-2xl font-bold text-white mb-6">⚙️ Account Settings</h2>
          <div className="space-y-4">
            {[
              { label: 'Notifications', status: 'Enabled' },
              { label: 'Dark Mode', status: 'Enabled' },
              { label: 'Two Factor Auth', status: 'Disabled' },
              { label: 'Email Updates', status: 'Enabled' },
            ].map((setting, idx) => (
              <div key={idx} className="flex justify-between items-center p-3 bg-slate-700/50 rounded-lg">
                <p className="text-gray-300">{setting.label}</p>
                <span className={`text-sm font-bold ${
                  setting.status === 'Enabled' ? 'text-green-400' : 'text-gray-400'
                }`}>{setting.status}</span>
              </div>
            ))}
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default Profile;